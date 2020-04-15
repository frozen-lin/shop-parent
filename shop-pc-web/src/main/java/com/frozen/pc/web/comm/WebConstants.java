package com.frozen.pc.web.comm;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <program> shop-parent </program>
 * <description> pc-web常量类 </description>
 *
 * @author : lw
 * @date : 2020-03-31 22:28
 **/
public class WebConstants {
    public static final String INDEX_PAGE = "/private/index";
    public static final String ERROR_PAGE = "/err";
    public static final String LOGIN_PAGE = "/login";
    public static final String REGISTER_PAGE = "/register";
    public static final String PAY_PAGE = "/private/pay";
    public static final String PAY_SUCCESS_PAGE = "/private/paySuccess";
    public static final String RELATION_PAGE = "relation";



    public static final String REDIRECT_PREFIX = "redirect:";

    /**
     * Cookie中登录token的键
     */
    public static final String TOKEN_COOKIE = "MEMBER_TOKEN";
    /**
     * GitHub中ACCESS_TOKEN的值
     */
    public static final String GIT_ACCESS_TOKEN = "GIT_ACCESS_TOKEN";

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
}
