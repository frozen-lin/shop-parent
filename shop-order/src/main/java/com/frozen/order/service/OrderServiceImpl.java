package com.frozen.order.service;

import com.frozen.order.api.OrderService;
import com.frozen.order.bean.OrderEntity;
import com.frozen.order.mapper.OrderMapper;
import com.frozen.order.utils.OrderNumberUtil;
import com.frozen.response.ResponseDataEntity;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <program> shop-parent </program>
 * <description> 订单用户实现类 </description>
 *
 * @author : lw
 * @date : 2020-04-03 15:20
 **/
@RestController
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public ResponseDataEntity<OrderEntity> queryOrderByOrderId(@RequestParam Long orderId) {
        OrderEntity orderEntity = orderMapper.selectByPrimaryKey(orderId);
        return ResponseUtil.getResponseSuccess(orderEntity);
    }

    @Override
    public ResponseDataEntity<OrderEntity> createOrder(@RequestBody OrderEntity orderEntity) {
        if (orderEntity.getUserId() == null) {
            return ResponseUtil.getResponseError("传入参数不正确,请检查", null);
        }
        orderEntity.setOrderNumber(OrderNumberUtil.createOrderNumber());
        orderMapper.insertSelective(orderEntity);
        return ResponseUtil.getResponseSuccess(orderEntity);
    }

    @Override
    public ResponseDataEntity<Object> payOrder(@RequestParam("orderId") Long orderId) {
        if (orderId == null) {
            return ResponseUtil.getResponseError("传入参数不正确,请检查", null);
        }
        Date now = new Date();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(orderId);
        //设置状态为已支付 和修改时间
        orderEntity.setIsPay(OrderEntity.PAY_STATE);
        orderEntity.setUpdated(now);
        int updateCount = orderMapper.updateByPrimaryKeySelective(orderEntity);
        if (updateCount <= 0) {
            return ResponseUtil.getResponseError("订单支付失败", null);
        }
        return ResponseUtil.getResponseSuccess("订单支付成功", null);
    }
}
