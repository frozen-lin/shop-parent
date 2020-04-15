package com.frozen.order.api;

import com.frozen.order.bean.OrderEntity;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <program> shop-parent </program>
 * <description> 订单服务接口 </description>
 *
 * @author : lw
 * @date : 2020-04-03 14:49
 **/
@RequestMapping("/order")
public interface OrderService {
    /**
     * <description> 创建订单 </description>
     * @param orderEntity : orderEntity包括userId
     * @return ResponseDataEntity<OrderEntity> :
     * @author : lw
     * @date : 2020/4/3 14:51
     */
    @PostMapping("/createOrder")
    ResponseDataEntity<OrderEntity> createOrder(@RequestBody OrderEntity orderEntity);

    /**
     * <description> 根据订单Id获取订单详情 </description>
     * @param orderId :
     * @author : lw
     * @return ResponseDataEntity<OrderEntity>:
     */
    @GetMapping("/queryOrderByOrderId")
    ResponseDataEntity<OrderEntity> queryOrderByOrderId(@RequestParam("orderId") Long orderId);

    /**
     * <description> 将订单支付状态修改为已支付 </description>
     * @param orderId : 订单Id
     * @author : lw
     * @return ResponseDataEntity<Object> :
     */
    @PostMapping("/payOrder")
    ResponseDataEntity<Object> payOrder(@RequestParam("orderId") Long orderId);
}
