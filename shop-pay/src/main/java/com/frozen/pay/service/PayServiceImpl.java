package com.frozen.pay.service;

import com.frozen.FrozenCommException;
import com.frozen.order.api.OrderService;
import com.frozen.order.bean.OrderEntity;
import com.frozen.pay.api.PayService;
import com.frozen.pay.bean.PaymentEntity;
import com.frozen.pay.comm.PayConstants;
import com.frozen.pay.mapper.PayMapper;
import com.frozen.pay.strategy.PayManager;
import com.frozen.pay.utils.PayUtils;
import com.frozen.response.ResponseDataEntity;
import com.frozen.service.RedisService;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <program> shop-parent </program>
 * <description> 支付服务 </description>
 *
 * @author : lw
 * @date : 2020-04-04 09:59
 **/
@Slf4j
@RestController
public class PayServiceImpl implements PayService {
    @Autowired
    private PayManager payManagerStrategy;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayMapper payMapper;

    @Override
    public ResponseDataEntity<String> getAlipayPage(@RequestParam("payToken") String payToken) {
        if (StringUtils.isBlank(payToken)) {
            ResponseUtil.getResponseError("未传支付token", null);
        }
        String payId = redisService.getString(payToken);
        if (StringUtils.isBlank(payId)) {
           return ResponseUtil.getResponseError("支付token已过期", null);
        }

        PaymentEntity paymentEntity = payMapper.selectByPrimaryKey(payId);
        try {
            String formHtml = payManagerStrategy.getPayManager(PayManager.ALI_PAY_MANAGER).payInfo(paymentEntity);
            return ResponseUtil.getResponseSuccess(formHtml);
        } catch (Exception e) {
            log.error("getAlipayPage出错啦", e);
            return ResponseUtil.getResponseError("getAlipayPage出错啦", null);
        }
    }

    @Override
    public ResponseDataEntity<String> createPay(@RequestBody PaymentEntity paymentEntity) throws FrozenCommException {
        //校验参数 userId,price
        if (paymentEntity.getUserId() == null || paymentEntity.getPrice() == null) {
            return ResponseUtil.getResponseError("参数不完整,请检查", null);
        }
        //创建订单
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(paymentEntity.getUserId());
        ResponseDataEntity<OrderEntity> orderResponse = orderService.createOrder(orderEntity);
        if (!ResponseUtil.checkResponseOk(orderResponse)) {
            throw new FrozenCommException("创建订单失败啦");
        }
        //保存支付信息
        paymentEntity.setOrderId(orderResponse.getData().getOrderId());
        payMapper.insertSelective(paymentEntity);
        //生成支付token
        String payToken = PayUtils.getPayToken();
        redisService.setString(payToken, paymentEntity.getPayId().toString(), PayConstants.PAY_TOKEN_EXPIRED_TIME);
        return ResponseUtil.getResponseSuccess(payToken);
    }
}
