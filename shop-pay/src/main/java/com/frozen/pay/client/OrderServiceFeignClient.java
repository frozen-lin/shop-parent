package com.frozen.pay.client;

import com.frozen.order.api.OrderService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-08 17:11
 **/
@FeignClient(name = "order-service")
public interface OrderServiceFeignClient extends OrderService {

}
