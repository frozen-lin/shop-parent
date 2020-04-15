package com.frozen.message.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <program> shopparent </program>
 * <description> 邮件实体类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 14:29
 **/
@Data
public class MailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 收件箱地址
     */
    private String toEmail;
    /**
     * 用户名
     */
    private String userName;
}
