package com.frozen.pc.web.controller;

import com.frozen.member.api.MemberCommService;
import com.frozen.member.bean.UserEntity;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.pc.web.utils.LoginUtil;
import com.frozen.response.ResponseDataEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> 登录注册功能Controller </description>
 *
 * @author : lw
 * @date : 2020-03-31 21:57
 **/
@Controller
@Slf4j
public class MemberController {
    @Autowired
    MemberCommService memberCommService;

    @PostMapping("/register")
    public String register(String username, String email, String password, RedirectAttributes redirectAttributes) {
        try {
            log.info("### 开始执行注册流程 ####");
            UserEntity userEntity = new UserEntity();
            userEntity.setPassword(password);
            userEntity.setEmail(email);
            userEntity.setUsername(username);
            log.info("### 调用注册服务开始 user实体类{}####", userEntity.toString());
            ResponseDataEntity<Object> register = memberCommService.register(userEntity);
            log.info("### 调用注册服务结束 ####");
            if (!register.getResCode().equals(HttpStatus.OK.value())) {
                redirectAttributes.addFlashAttribute("error", register.getResMsg());
                return WebConstants.REDIRECT_PREFIX + WebConstants.ERROR_PAGE;
            }
            redirectAttributes.addFlashAttribute("msg", register.getResMsg());
            return WebConstants.REDIRECT_PREFIX + WebConstants.LOGIN_PAGE;
        } catch (Exception e) {
            log.error("注册异常", e);
            redirectAttributes.addFlashAttribute("error", "出错啦");
            return WebConstants.REDIRECT_PREFIX + WebConstants.ERROR_PAGE;
        } finally {
            log.info("### 执行注册流程结束 ####");
        }
    }

    @PostMapping("/login")
    public String login(String username, String password, RedirectAttributes redirectAttributes, HttpServletResponse resp) {
        try {
            //1.封装用户实体
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            ResponseDataEntity<Map<String, String>> mapResponse = memberCommService.login(userEntity);
            if (!mapResponse.getResCode().equals(HttpStatus.OK.value())) {
                //2.登录失败 重定向至登录页面 并显示错误信息
                redirectAttributes.addFlashAttribute("error", mapResponse.getResMsg());
                return WebConstants.REDIRECT_PREFIX + WebConstants.LOGIN_PAGE;
            }
            //3.登录成功 将登录token放入cookie中
            return LoginUtil.addLoginToken(resp, mapResponse, redirectAttributes);
        } catch (Exception e) {
            log.error("登录异常", e);
            redirectAttributes.addFlashAttribute("error", "出错啦");
            return WebConstants.REDIRECT_PREFIX + WebConstants.ERROR_PAGE;
        } finally {
            log.info("### 执行登录流程结束 ####");
        }
    }
}

