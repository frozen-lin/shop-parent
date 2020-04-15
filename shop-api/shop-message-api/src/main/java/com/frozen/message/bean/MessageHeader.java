package com.frozen.message.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <program> shopparent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-03-27 14:47
 **/
@Data
public class MessageHeader implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 接口类型
     */
    private String interfaceType;
}
