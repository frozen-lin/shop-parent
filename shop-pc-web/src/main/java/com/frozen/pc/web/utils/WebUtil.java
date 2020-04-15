package com.frozen.pc.web.utils;

import com.frozen.member.api.MemberCommService;
import com.frozen.member.bean.UserEntity;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.response.ResponseDataEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <program> shop-parent </program>
 * <description> 站点工具类 </description>
 *
 * @author : lw
 * @date : 2020-04-10 17:23
 **/
public class WebUtil {
    private static MemberCommService memberCommService = SpringContextUtil.getBean(MemberCommService.class);
    /**
     * <description> 获取request对象 </description>
     *
     * @return : javax.servlet.http.HttpServletRequest
     * @author : lw
     * @date : 2020/4/10 17:24
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * <description> 获取response对象 </description>
     *
     * @return : javax.servlet.http.HttpServletResponse
     * @author : lw
     * @date : 2020/4/10 17:24
     */
    public static HttpServletResponse getResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static UserEntity getLoginUser() {
        String token = getLoginToken();
        //判断token是否有效
        ResponseDataEntity<UserEntity> responseData = memberCommService.queryLoginUserByToken(token);
        if (!responseData.getResCode().equals(HttpStatus.OK.value())) {
            return null;
        }
        return responseData.getData();
    }

    /**
     * <description> 获取登录token </description>
     *
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/11 15:19
     */
    public static String getLoginToken() {
        HttpServletRequest request = getRequest();
        Cookie[] cookies = getRequest().getCookies();
        return CookieUtil.getCookie(cookies, WebConstants.TOKEN_COOKIE);
    }

}
