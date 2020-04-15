package com.frozen.message.service;

import com.alibaba.fastjson.JSONObject;
import com.frozen.message.adaptor.MessageAdaptor;
import com.frozen.message.constants.ExposeMsgConstants;
import com.frozen.message.bean.MailEntity;
import com.frozen.message.bean.MailMsg;
import com.frozen.message.bean.MailMessageEntity;
import com.frozen.message.bean.MessageHeader;
import com.frozen.message.component.MessageAdaptorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * <program> shopparent </program>
 * <description> 注册消费者接口 </description>
 *
 * @author : lw
 * @date : 2020-03-27 14:24
 **/
@Slf4j
@Service
public class RegisterDistribute {
    @Value(value = "${register-mail.subject}")
    private String subject;
    @Value(value = "${register-mail.text}")
    private String text;
    @Autowired
    private MessageAdaptorStrategy adaptorStrategy;

    @JmsListener(destination = ExposeMsgConstants.REGISTER_QUEUE)
    public void distribute(String json) {
        log.info("----开始处理注册发送邮件业务---");
        log.info("接收参数:{}",json);
        if (StringUtils.isBlank(json)) {
            return;
        }
        MailMessageEntity msgEntity;
        try {
            msgEntity = JSONObject.parseObject(json, MailMessageEntity.class);
        } catch (Exception e) {
            log.warn("json数据:|{}|有误", json);
            return;
        }
        //校验消息头
        MessageHeader header = msgEntity.getMsgHeader();
        if (header == null || !ExposeMsgConstants.MAIL_INTERFACE_TYPE.equals(header.getInterfaceType())) {
            log.warn("json数据:|{}|消息头接口类型有误", json);
            return;
        }
        //校验消息体
        MailEntity mailEntity = msgEntity.getBody();
        if (mailEntity==null||StringUtils.isBlank(mailEntity.getToEmail())) {
            log.warn("json数据:|{}|邮件消息体有误", json);
            return;
        }
        MailMsg msg = new MailMsg();
        msg.setToEmail(mailEntity.getToEmail());
        msg.setSubject(this.subject);
        msg.setText(this.text.replace("{user}", mailEntity.getUserName()));
        MessageAdaptor messageAdaptor = adaptorStrategy.getMessageAdaptor(header.getInterfaceType());
        messageAdaptor.sendMsg(msg);
        log.info("----处理注册发送邮件业务完成---");
    }
}
