package com.frozen.member.utils;

import com.frozen.member.common.MemberConstants;

import java.util.UUID;

/**
 * <program> shopparent </program>
 * <description> 令牌工具类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 11:09
 **/
public class MemberTokenUtil {
    public static String getToken(){
        return MemberConstants.SERVICE_PREFIX+ UUID.randomUUID().toString();
    }
}
