package com.frozen.pc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.frozen.pc.web.bean.TextMessage;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.pc.web.utils.HttpClientUtil;
import com.frozen.pc.web.utils.WxUtil;
import com.frozen.pc.web.utils.XmlUtil;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 19:52
 **/
@RestController()
@RequestMapping("/wx")
@Slf4j
public class WxController {
    private static final String CHAT_API_URL = "http://api.qingyunke.com/api.php";

    @GetMapping("/dispatcher")
    public String dispatcher(String signature, String timestamp, String nonce, String echostr) {
        if (WxUtil.checkSignautre(signature, timestamp, nonce, echostr)) {
            return echostr;
        }
        return null;
    }

    @PostMapping(value = "/dispatcher")
    public String dispatcher(HttpServletRequest request, String msg_signature, String timestamp, String nonce) throws Exception {
        //获取输入流内容
        final InputStream inputStream = request.getInputStream();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                sb.append(string);
            }
        }
        log.info(sb.toString());
        //1.对流中内容进行解密
        String fromXml = WxUtil.decryptMsg(msg_signature, timestamp, nonce, sb.toString());

        TextMessage fromMessage = XmlUtil.xmlToBean(fromXml, TextMessage.class);
        //2.请求第三方机器人接口
        Map<String, String> paramMap = new HashMap<>(3);
        paramMap.put("key", "free");
        paramMap.put("appid", "0");
        paramMap.put("msg", fromMessage.getContent());
        ResponseHandler<String> responseHandler = response -> {
            String responseMsg = "我也不知道回答些什么";
            if (ResponseUtil.OK_VALUE.equals(response.getStatusLine().getStatusCode())) {
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), WebConstants.DEFAULT_CHARSET));
                if ("0".equals(jsonObject.getString("result"))) {
                    return jsonObject.getString("content");
                }
            }else{
                log.error("请求机器人失败");
            }
            return responseMsg;
        };
        String toXml = setTextMsg(HttpClientUtil.doGet(CHAT_API_URL, null, paramMap, responseHandler), fromMessage.getToUserName(), fromMessage.getFromUserName());
        toXml = WxUtil.encryptMsg(toXml, timestamp, nonce);
        log.info(toXml);
        return toXml;
    }

    private String setTextMsg(String content, String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(fromUserName);
        textMessage.setToUserName(toUserName);
        textMessage.setContent(content);
        textMessage.setMsgType("text");
        textMessage.setCreateTime(System.currentTimeMillis());
        String messageToXml = XmlUtil.beanToXml(textMessage);
        log.info("####setTextMess()###messageToXml:" + messageToXml);
        return messageToXml;
    }

}
