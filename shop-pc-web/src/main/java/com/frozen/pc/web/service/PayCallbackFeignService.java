package com.frozen.pc.web.service;

import com.frozen.pay.api.PayCallbackService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <program> shop-parent </program>
 * <description> 支付回调Feign客户端 </description>
 *
 * @author : lw
 * @date : 2020-04-11 15:10
 **/
@FeignClient("pay-service")
public interface PayCallbackFeignService extends PayCallbackService {
}
