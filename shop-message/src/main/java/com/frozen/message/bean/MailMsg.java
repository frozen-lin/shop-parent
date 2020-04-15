package com.frozen.message.bean;

import lombok.Data;

/**
 * <program> shopparent </program>
 * <description> 邮件信息实体类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 14:10
 **/
@Data
public class MailMsg {
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件正文
     */
    private String text;
    /**
     * 收件箱
     */
    private String toEmail;
}
