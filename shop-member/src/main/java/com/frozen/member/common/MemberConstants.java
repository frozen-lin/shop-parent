package com.frozen.member.common;

/**
 * <program> shop-parent </program>
 * <description> 常量类 </description>
 *
 * @author : lw
 * @date : 2020-03-26 21:10
 **/
public class MemberConstants {
    /**
     * 密码前缀加盐
     */
    public static final String PASSWORD_SALT_PREFIX = "MEMBER";
    /**
     * Member前缀
     */
    public static final String SERVICE_PREFIX = "member-";
    /**
     * 用户登录过期时长
     */
    public static final Long LOGIN_EXPIRE_TIME = 90L;
}
