package com.frozen.member.utils;

import com.frozen.member.common.MemberConstants;

/**
 * <program> shop-parent </program>
 * <description> Redis工具类 </description>
 *
 * @author : lw
 * @date : 2020-03-27 11:12
 **/
public class MemberRedisUtil {
    public static String getRedisKey(String originKey){
        return MemberConstants.SERVICE_PREFIX+originKey;
    }
}
