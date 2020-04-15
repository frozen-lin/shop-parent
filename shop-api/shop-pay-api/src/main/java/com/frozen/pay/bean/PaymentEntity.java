package com.frozen.pay.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  payment_info
 * @author lin 2020-04-03
 */
@Data
@Table(name = "payment_info")
public class PaymentEntity implements Serializable {

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
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payId;

    /**
     * userid
     */
    @Column(name = "userId")
    private Long userId;

    /**
     * typeid
     */
    @Column(name = "typeId")
    private Integer typeId;

    /**
     * orderid
     */
    @Column(name = "orderId")
    private Long orderId;

    /**
     * price
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * source
     */
    @Column(name = "source")
    private String source;

    /**
     * state
     */
    @Column(name = "state")
    private Integer state;

    /**
     * created
     */
    private Date created;

    /**
     * updated
     */
    private Date updated;

    /**
     * platformorderid 平台订单id
     */
    @Column(name = "platformorderId")
    private String platformorderId;
}
