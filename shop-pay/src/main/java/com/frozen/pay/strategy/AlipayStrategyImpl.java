package com.frozen.pay.strategy;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.frozen.pay.bean.AlipayBizContent;
import com.frozen.pay.bean.PaymentEntity;
import com.frozen.pay.comm.PayConstants;
import com.frozen.pay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 阿里支付服务实现类 </description>
 *
 * @author : lw
 * @date : 2020-04-04 22:30
 **/
@Service(PayManager.ALI_PAY_MANAGER)
public class AlipayStrategyImpl implements IPayStrategy {
    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayConfig alipayConfig;
    @Override
    public String payInfo(PaymentEntity paymentEntity) throws Exception{
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayBizContent alipayBizContent = new AlipayBizContent();
        alipayBizContent.setOperator_id(paymentEntity.getUserId().toString());
        alipayBizContent.setOut_trade_no(paymentEntity.getOrderId().toString());
        alipayBizContent.setSubject("测试商品");
        alipayBizContent.setTotal_amount(paymentEntity.getPrice().toString());
        request.setBizContent(alipayBizContent.toString());
        request.setNotifyUrl(PayConstants.notifyUrl);
        request.setReturnUrl(PayConstants.returnUrl);
        return alipayClient.pageExecute(request).getBody();
    }

    @Override
    public boolean checkRSA(Map<String, String> mapParams) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(mapParams, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
    }
}
