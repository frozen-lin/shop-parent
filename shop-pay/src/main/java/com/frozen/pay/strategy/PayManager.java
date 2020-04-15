package com.frozen.pay.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 支付策略 </description>
 *
 * @author : lw
 * @date : 2020-04-04 22:39
 **/
@Component
public class PayManager {
    private Map<String, IPayStrategy> payManagerMap = new HashMap<>();
    public static final String ALI_PAY_MANAGER = "alipayService";

    @Autowired
    public PayManager(Map<String, IPayStrategy> payManagerMap) {
        payManagerMap.forEach(this.payManagerMap::put);
    }

    public IPayStrategy getPayManager(String strategy) {
        return payManagerMap.get(strategy);
    }
}
