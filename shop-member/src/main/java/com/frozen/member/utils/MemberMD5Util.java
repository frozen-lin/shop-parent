package com.frozen.member.utils;

import com.frozen.member.common.MemberConstants;
import com.frozen.utils.MD5Util;

import java.util.UUID;

/**
 * <program> shopparent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-03-26 21:09
 **/
public class MemberMD5Util {
    public final static String encryptPassword(String originPassword,String salt){
        return MD5Util.MD5(MemberConstants.PASSWORD_SALT_PREFIX +originPassword+salt);
    }
    /**
     * <description> 获取MD5盐 </description>
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/3/26 21:14
     */
    public final static String getSalt(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
