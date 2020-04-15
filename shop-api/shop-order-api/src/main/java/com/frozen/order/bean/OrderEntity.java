package com.frozen.order.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <program> shop-parent </program>
 * <description> Order实体类 </description>
 *
 * @author : lw
 * @date : 2020-04-03 14:31
 **/
@Table(name = "order_info")
@Data
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 已支付状态
     */
    public static final Integer PAY_STATE = 1;
    /**
     * 未支付状态
     */
    public static final Integer NOT_PAY_STATE = 0;
    /**
     * `id` int(11) NOT NULL AUTO_INCREMENT,
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    /**
     * `orderNumber` varchar(255) DEFAULT NULL COMMENT '订单编号',
     */
    @Column(name="orderNumber")
    private String orderNumber;
    /**
     * `isPay` int(50) DEFAULT NULL COMMENT '0 未支付，1已支付',
     */
    @Column(name="isPay")
    private Integer isPay;
    /**
     * `userId` int(50) DEFAULT NULL, 用户id
     */
    @Column(name="userId")
    private Long userId;
    /**
     * `created` datetime NOT NULL, 创建时间
     */
    private Date created;
    /**
     * `updated` datetime NOT NULL, 更新时间
     */
    private Date updated;
}
