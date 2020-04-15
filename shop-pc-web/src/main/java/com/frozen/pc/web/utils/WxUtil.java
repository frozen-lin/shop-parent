package com.frozen.pc.web.utils;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 20:05
 **/
@Slf4j
public class WxUtil {

    private static final String TOKEN = "1215";
    private static WXBizMsgCrypt wxcpt;

    static {
        try {
            wxcpt = new WXBizMsgCrypt(WxUtil.TOKEN, WxUtil.ENCODING_AES_KEY, WxUtil.APP_ID);
        } catch (AesException e) {
            log.error("微信解密初始化异常", e);
        }
    }

    private static final String ENCODING_AES_KEY = "cUd9AtjJfM7i1wrjx3bBYqWKrWdV66p3Zeu4aaBNdAK";
    private static final String APP_ID = "wx5b27f4b275dd3861";

    public static boolean checkSignautre(String signature, String timestamp, String nonce, String echostr) {
        try {
            String temp = wxcpt.verifyURL(signature, timestamp, nonce);
            return temp.equals(signature);
        } catch (Exception e) {
            log.error("微信验证失败", e);
            return false;
        }
    }

    public static String decryptMsg(String signature, String timestamp, String nonce, String data) {
        String decryptMsg = "";
        try {
            decryptMsg = wxcpt.decryptMsg(signature, timestamp, nonce, data);
        } catch (AesException e) {
            log.error("微信解密异常", e);
        }
        return decryptMsg;
    }

    public static String encryptMsg(String replyMsg, String timestamp, String nonce) {
        String encryptMsg = "";
        try {
            encryptMsg = wxcpt.encryptMsg(replyMsg,timestamp,nonce);
        } catch (AesException e) {
            log.error("微信加密异常", e);
        }
        return encryptMsg;
    }
}
