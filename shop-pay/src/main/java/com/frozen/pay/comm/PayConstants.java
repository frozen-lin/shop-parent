package com.frozen.pay.comm;

/**
 * <program> shop-parent </program>
 * <description> pay常量 </description>
 *
 * @author : lw
 * @date : 2020-04-04 20:35
 **/
public class PayConstants {
    /**
     * 异步回调地址
     */
    public static final String notifyUrl = "http://server.natappfree.cc:39558/pay/alipay/notify";
    /**
     * 同步回调地址
     */
    public static final String returnUrl = "http://server.natappfree.cc:39558/private/pay/alipay/return";
    /**
     * 支付token过期时间 15min
     */
    public static final Long PAY_TOKEN_EXPIRED_TIME = 15*60L;
}
