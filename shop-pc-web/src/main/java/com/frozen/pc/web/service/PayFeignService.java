package com.frozen.pc.web.service;

import com.frozen.pay.api.PayCallbackService;
import com.frozen.pay.api.PayService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <program> shop-parent </program>
 * <description> 支付Feign客户端 </description>
 *
 * @author : lw
 * @date : 2020-04-11 15:07
 **/
@FeignClient(name = "pay-service")
public interface PayFeignService extends PayService{

}
