package com.frozen.pay.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-04 14:05
 **/
@Data
public class AlipayBizContent {
    /**
     * 订单号
     */
    private String out_trade_no;
    /**
     * 用户Id
     */
    private String operator_id;
    /**
     * 总金额
     */
    private String total_amount;
    /**
     * 商品标题
     */
    private String subject;
    /**
     * 商品描述
     */
    private String body;
    private String product_code = "FAST_INSTANT_TRADE_PAY";
    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}
