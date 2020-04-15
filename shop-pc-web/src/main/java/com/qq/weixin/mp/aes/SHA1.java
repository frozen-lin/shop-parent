package com.qq.weixin.mp.aes;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * SHA1 class
 * <p>
 * 计算公众平台的消息签名接口.
 */
@Slf4j
class SHA1 {

    /**
     * 用SHA1算法生成安全签名
     *
     * @param token     票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param encrypt   密文
     * @return 安全签名
     * @throws AesException
     */
    public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException {
        try {
            String[] array = new String[]{token, timestamp, nonce, encrypt};
            return getSHA1(array);
        } catch (Exception e) {
            log.error("生成安全签名异常", e);
            throw new AesException(AesException.ComputeSignatureError);
        }
    }

    /**
     * 用SHA1算法生成安全签名
     *
     * @param token     票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @return 安全签名
     * @throws AesException
     */
    public static String getSHA1(String token, String timestamp, String nonce) throws AesException {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            return getSHA1(array);
        } catch (Exception e) {
			log.error("生成安全签名异常", e);
            throw new AesException(AesException.ComputeSignatureError);
        }
    }

    public static String getSHA1(String[] array) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        // 字符串排序
        Arrays.sort(array);
		for (String s : array) {
			sb.append(s);
		}
        String str = sb.toString();
        // SHA1签名生成
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuilder hexStr = new StringBuilder();
        String shaHex;
        for (byte b : digest) {
            shaHex = Integer.toHexString(b & 0xFF);
            if (shaHex.length() < 2) {
                hexStr.append(0);
            }
            hexStr.append(shaHex);
        }
        return hexStr.toString();
    }
}
