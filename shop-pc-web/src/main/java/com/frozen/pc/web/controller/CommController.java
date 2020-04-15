package com.frozen.pc.web.controller;

import com.frozen.member.api.MemberCommService;
import com.frozen.member.bean.UserEntity;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.pc.web.utils.CookieUtil;
import com.frozen.response.ResponseDataEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * <program> shop-parent </program>
 * <description> 基础转发页面Controller </description>
 *
 * @author : lw
 * @date : 2020-03-31 22:00
 **/
@Controller
public class CommController {
    @Autowired
    MemberCommService memberCommService;

    @GetMapping("/private/index")
    public String toIndex(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Cookie[] cookies = request.getCookies();
        //从Cookie获取token
        String token = CookieUtil.getCookie(cookies, WebConstants.TOKEN_COOKIE);
        ResponseDataEntity<UserEntity> responseData = memberCommService.queryLoginUserByToken(token);
        if (!responseData.getResCode().equals(HttpStatus.OK.value())) {
            redirectAttributes.addFlashAttribute("error", responseData.getResMsg());
            return WebConstants.REDIRECT_PREFIX + WebConstants.ERROR_PAGE;
        }
        request.setAttribute("userName", responseData.getData().getUsername());
        return WebConstants.INDEX_PAGE;
    }

    @GetMapping("/private/pay")
    public String toPay() {
        return WebConstants.PAY_PAGE;
    }

    @GetMapping("/register")
    public String toRegister() {
        return WebConstants.REGISTER_PAGE;
    }

    @GetMapping("/login")
    public String toLogin() {
        return WebConstants.LOGIN_PAGE;
    }

    @GetMapping("/err")
    public String toError() {
        return WebConstants.ERROR_PAGE;
    }

    @GetMapping("/relation")
    public String toRelation() {
        return WebConstants.RELATION_PAGE;
    }
}
