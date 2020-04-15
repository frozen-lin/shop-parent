package com.frozen.message.service;

import com.frozen.message.constants.ExposeMsgConstants;
import com.frozen.message.adaptor.MessageAdaptor;
import com.frozen.message.bean.MailMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * <program> shopparent </program>
 * <description> 邮件发送消息实现类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 13:51
 **/
@Slf4j
@Service(ExposeMsgConstants.MAIL_INTERFACE_TYPE)
public class MailService implements MessageAdaptor {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Override
    public void sendMsg(Object message) {
        if (!(message instanceof MailMsg)) {
            return;
        }
        MailMsg msg = (MailMsg) message;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        log.info(fromEmail);
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(msg.getToEmail());
        simpleMailMessage.setSubject(msg.getSubject());
        simpleMailMessage.setText(msg.getText());
        mailSender.send(simpleMailMessage);
    }
}
