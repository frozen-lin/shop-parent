package com.frozen.pay.utils;

import java.util.UUID;

/**
 * <program> shop-parent </program>
 * <description> 支付工具类 </description>
 *
 * @author : lw
 * @date : 2020-04-08 17:02
 **/
public class PayUtils {
    private static final String PAY_TOKEN_PREFIX = "TOKEN_PAY";

    /**
     * <description> 获取支付token </description>
     *
     * @return : java.lang.String :支付token
     * @author : lw
     * @date : 2020/4/8 17:05
     */
    public static String getPayToken() {
        return PAY_TOKEN_PREFIX + UUID.randomUUID();
    }
}
