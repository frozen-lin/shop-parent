package com.frozen.pc.web.utils;

import com.frozen.pc.web.comm.WebConstants;
import com.frozen.response.ResponseDataEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description>  </description>
 *
 * @author : lw
 * @date : 2020-04-13 11:50
 **/
public class LoginUtil {
    /**
     * <description> 设置登录token,并返回跳转页面 </description>
     * @param response :
     * @param mapResponse :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/13 11:51
     */
    public static String addLoginToken(HttpServletResponse response, ResponseDataEntity<Map<String, String>> mapResponse, RedirectAttributes redirectAttributes){
        Map<String, String> dataMap = mapResponse.getData();
        String token = dataMap.get("token");
        Cookie cookie = new Cookie(WebConstants.TOKEN_COOKIE, token);
        response.addCookie(cookie);
        redirectAttributes.addFlashAttribute("msg", mapResponse.getResMsg());
        return WebConstants.REDIRECT_PREFIX + WebConstants.INDEX_PAGE;
    }
}
