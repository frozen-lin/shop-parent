package com.frozen.pc.web.bean;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 20:29
 **/
@Data()
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextMessage{
    /**
     * 开发者微信
     */
    @XmlElement(name = "ToUserName")
    protected String toUserName;
    /**
     * 发送方openid
     */
    @XmlElement(name = "FromUserName")
    protected String fromUserName;
    /**
     * 创建时间
     */
    @XmlElement(name = "CreateTime")
    protected Long createTime;
    /**
     * 内容类型
     */
    @XmlElement(name = "MsgType")
    protected String msgType;
    /**
     * 消息id
     */
    @XmlElement(name = "MsgId")
    protected Long msgId;

    @XmlElement(name = "Content")
    private String content;
}

