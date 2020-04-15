package com.frozen.message.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <program> shopparent </program>
 * <description> 消息队列接口类型 </description>
 *
 * @author : lw
 * @date : 2020-03-27 14:46
 **/
@Data
public class MailMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private MessageHeader msgHeader;
    private MailEntity body;

    public MailMessageEntity(){

    }

    public MailMessageEntity(String interfaceType,String userName,String toEmail){
        this.msgHeader = new MessageHeader();
        msgHeader.setInterfaceType(interfaceType);
        this.body = new MailEntity();
        this.body.setUserName(userName);
        this.body.setToEmail(toEmail);
    }
}
