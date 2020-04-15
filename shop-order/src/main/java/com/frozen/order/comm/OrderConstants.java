package com.frozen.order.comm;

/**
 * <program> shop-parent </program>
 * <description> 订单常量类 </description>
 *
 * @author : lw
 * @date : 2020-04-03 16:44
 **/
public class OrderConstants {

    public static final String ORDER_PREFIX = "ORDER";

    public static final String TOKEN_PREFIX = "TOKEN"+ORDER_PREFIX;
    /**
     * 订单失效时间 15min
     */
    public static final Long ORDER_EXPIRE_TIME = 15*60L;
}
