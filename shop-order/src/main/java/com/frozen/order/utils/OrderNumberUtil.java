package com.frozen.order.utils;

import com.frozen.utils.DateUtils;

import java.time.LocalDate;
import java.util.UUID;

/**
 * <program> shop-parent </program>
 * <description> 订单orderNum生成工具类 </description>
 *
 * @author : lw
 * @date : 2020-04-07 14:37
 **/
public class OrderNumberUtil {
    /**
     * <description> 创建新订单号 </description>
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/7 14:50
     */
    public static String createOrderNumber(){
        LocalDate today = LocalDate.now();
        return DateUtils.shortDateFormat(today)+ UUID.randomUUID().toString();
    }
}
