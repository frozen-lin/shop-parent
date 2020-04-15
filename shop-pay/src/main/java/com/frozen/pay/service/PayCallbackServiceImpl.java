package com.frozen.pay.service;

import com.frozen.FrozenCommException;
import com.frozen.order.api.OrderService;
import com.frozen.order.bean.OrderEntity;
import com.frozen.pay.api.PayCallbackService;
import com.frozen.pay.bean.PayInfo;
import com.frozen.pay.bean.PaymentEntity;
import com.frozen.pay.mapper.PayMapper;
import com.frozen.pay.strategy.IPayStrategy;
import com.frozen.pay.strategy.PayManager;
import com.frozen.response.ResponseDataEntity;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-10 10:47
 **/
@RestController
@Slf4j
public class PayCallbackServiceImpl implements PayCallbackService {
    @Autowired
    private PayMapper payMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayManager payManagerStrategy;

    @Override
    public ResponseDataEntity<String> asyncCallBack(@RequestBody Map<String, String> mapParams) throws FrozenCommException {
        //验签
        PayInfo payInfo = checkAliRSAAndGetPayInfo(mapParams);
        //创建查询条件
        Example example = new Example(PaymentEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", payInfo.getOrderId());
        criteria.andEqualTo("state", PaymentEntity.PAY_STATE);
        criteria.andEqualTo("platformorderId", payInfo.getTradeNo());
        int payCount = payMapper.selectCountByExample(example);
        log.info(payCount + "");
        if (payCount > 0) {
            log.info("重复异步回调");
            return ResponseUtil.getResponseSuccess("异步回调成功", "success");
        }
        ResponseDataEntity<OrderEntity> orderResponse = orderService.queryOrderByOrderId(payInfo.getOrderId());
        if (!ResponseUtil.OK_VALUE.equals(orderResponse.getResCode())) {
            log.error(orderResponse.toString());
            throw new FrozenCommException("出错啦");
        }
        //更新支付状态
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setOrderId(payInfo.getOrderId());
        paymentEntity.setState(PaymentEntity.PAY_STATE);
        paymentEntity.setPlatformorderId(payInfo.getTradeNo());
        paymentEntity.setPrice(payInfo.getTotalAmount());
        paymentEntity.setUserId(orderResponse.getData().getUserId());
        paymentEntity.setUpdated(new Date());
        example = new Example(PaymentEntity.class);
        criteria = example.createCriteria();
        criteria.andEqualTo("orderId", payInfo.getOrderId());
        criteria.andEqualTo("state", PaymentEntity.NOT_PAY_STATE);
        int i = payMapper.updateByExampleSelective(paymentEntity, example);
        if (i > 0) {
            //更新订单状态
            ResponseDataEntity<Object> responseDataEntity = orderService.payOrder(payInfo.getOrderId());
            if (!ResponseUtil.checkResponseOk(responseDataEntity)) {
                throw new FrozenCommException(responseDataEntity.getResMsg());
            }
        }
        return ResponseUtil.getResponseSuccess("异步回调成功", "success");
    }

    @Override
    public ResponseDataEntity<PayInfo> syncCallBack(@RequestBody Map<String, String> mapParams) throws FrozenCommException {
        //验签
        PayInfo payInfo = checkAliRSAAndGetPayInfo(mapParams);
        log.info("同步回调成功{}", payInfo.toString());
        return ResponseUtil.getResponseSuccess(payInfo);
    }

    /**
     * <description> 阿里校验支付参数 并返回支付信息 </description>
     *
     * @param mapParams :
     * @return : com.frozen.pay.bean.PayInfo
     * @author : lw
     * @date : 2020/4/10 13:42
     */
    private PayInfo checkAliRSAAndGetPayInfo(Map<String, String> mapParams) throws FrozenCommException {
        if (Objects.isNull(mapParams)) {
            throw new FrozenCommException("请传入参数");
        }
        try {
            //验签
            IPayStrategy payManagerService = payManagerStrategy.getPayManager(PayManager.ALI_PAY_MANAGER);
            if (!payManagerService.checkRSA(mapParams)) {
                throw new FrozenCommException("验签失败");
            }
        } catch (Exception e) {
            log.error("验签出错啦:", e);
            throw new FrozenCommException("验签出错啦");
        }
        PayInfo payInfo = new PayInfo();
        payInfo.setOrderId(Long.valueOf(mapParams.get("out_trade_no")));
        payInfo.setTotalAmount(new BigDecimal(mapParams.get("total_amount")));
        payInfo.setTradeNo(mapParams.get("trade_no"));
        return payInfo;
    }
}
