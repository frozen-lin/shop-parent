package com.frozen.pay.strategy;

import com.alipay.api.AlipayApiException;
import com.frozen.pay.bean.PaymentEntity;

import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 支付服务接口 </description>
 *
 * @author : lw
 * @date : 2020-04-04 22:27
 **/
public interface IPayStrategy {
    /**
     * <description> 获取跳转支付页面代码 </description>
     * @param paymentEntity :
     * @return : java.lang.String 返回跳转支付页面代码
     * @author : lw
     * @date : 2020/4/4 22:29
     * @throws Exception: 异常
     */
    String payInfo(PaymentEntity paymentEntity) throws Exception;

    boolean checkRSA(Map<String,String> mapParam) throws AlipayApiException;
}
