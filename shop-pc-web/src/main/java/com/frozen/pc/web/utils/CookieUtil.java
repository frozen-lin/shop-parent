package com.frozen.pc.web.utils;

import javax.servlet.http.Cookie;

/**
 * <program> shop-parent </program>
 * <description> Cookie工具类 </description>
 *
 * @author : lw
 * @date : 2020-04-01 13:14
 **/
public class CookieUtil {
    public static String getCookie(Cookie[] cookies, String cookieKey) {
        if (cookies == null || cookieKey == null) {
            return null;
        }
        String value = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieKey)) {
                value = cookie.getValue();
                break;
            }
        }
        return value;

    }
}
