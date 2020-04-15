package com.frozen.pay.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <program> shop-parent </program>
 * <description> 支付信息 </description>
 *
 * @author : lw
 * @date : 2020-04-10 13:31
 **/
@Data
public class PayInfo {
    /**
     * 支付金额
     */
    private BigDecimal totalAmount;
    /**
     * 支付宝交易号
     */
    private String tradeNo;
    /**
     * 订单Id
     */
    private Long orderId;
}
