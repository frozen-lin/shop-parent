package com.frozen.pc.web.controller;

import com.frozen.FrozenCommException;
import com.frozen.member.bean.UserEntity;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.pc.web.service.MemberCommFeignService;
import com.frozen.pc.web.service.MemberGitHubFeignService;
import com.frozen.pc.web.utils.CookieUtil;
import com.frozen.pc.web.utils.GitHubUtil;
import com.frozen.pc.web.utils.LoginUtil;
import com.frozen.response.ResponseDataEntity;
import com.frozen.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <program> shop-parent </program>
 * <description> qq登录controller </description>
 *
 * @author : lw
 * @date : 2020-04-01 17:31
 **/
@Controller
@Slf4j
public class GitHubAuthController {
    @Autowired
    private MemberGitHubFeignService memberGitHubService;
    @Autowired
    private MemberCommFeignService memberCommService;

    /**
     * <description> 重定向至github登录授权页面 </description>
     *
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/12 13:41
     */
    @GetMapping("/gitHubConnectLogin")
    public String toGitHubConnectLogin() {
        return WebConstants.REDIRECT_PREFIX + GitHubUtil.generategitAuthorizeUrl();
    }

    /**
     * <description> github登录完成后重定向回调地址 </description>
     *
     * @param code               :
     * @param redirectAttributes :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/13 10:28
     */
    @GetMapping("/gitLoginCallback")
    public String gitLoginCallback(String code, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        try {
            log.info("###github授权流程开始###");
            String accessToken = GitHubUtil.getAccessToken(code);
            String gitHubUserId = GitHubUtil.accessGitHubUserId(accessToken);
            return loginByGitUserId(gitHubUserId, accessToken, response, redirectAttributes);
        } catch (Exception e) {
            log.error("授权失败", e);
            redirectAttributes.addFlashAttribute("error", "github授权失败");
            return WebConstants.REDIRECT_PREFIX + WebConstants.ERROR_PAGE;
        } finally {
            log.info("###github授权流程结束###");
        }
    }

    /**
     * <description> 根据gitHubUserId登录 </description>
     *
     * @param gitHubUserId       : gitHub用户Id
     * @param gitAccessToken     : gitHub access token
     * @param response           :
     * @param redirectAttributes :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/13 13:19
     */
    private String loginByGitUserId(String gitHubUserId, String gitAccessToken, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(gitHubUserId)) {
            redirectAttributes.addFlashAttribute("error", "github授权失败");
            return WebConstants.REDIRECT_PREFIX + WebConstants.ERROR_PAGE;
        }
        ResponseDataEntity<Map<String, String>> mapResponse = memberGitHubService.gitLogin(gitHubUserId);
        if (!ResponseUtil.checkResponseOk(mapResponse)) {
            //将GIT_ACCESS_TOKEN存入cookie中
            response.addCookie(new Cookie("GIT_ACCESS_TOKEN", gitAccessToken));
            return WebConstants.REDIRECT_PREFIX + WebConstants.RELATION_PAGE;
        }
        //登录成功清除 gitAccessToken
        clearGitAccessTokenCookie(response);
        return LoginUtil.addLoginToken(response, mapResponse, redirectAttributes);
    }

    /**
     * <description> 登录账户并绑定gihub用户id </description>
     *
     * @param username           : 用户名
     * @param password           : 密码
     * @param request            :
     * @param resp               :
     * @param redirectAttributes :
     * @return : java.lang.String
     * @author : lw
     * @date : 2020/4/13 13:38
     */
    @PostMapping("/gitLoginAndBindUser")
    public String gitLoginAndBindUser(String username, String password, HttpServletRequest request, HttpServletResponse resp, RedirectAttributes redirectAttributes) {
        Cookie[] cookies = request.getCookies();
        String gitAccessToken = CookieUtil.getCookie(cookies, WebConstants.GIT_ACCESS_TOKEN);
        if (StringUtils.isBlank(gitAccessToken)) {
            redirectAttributes.addFlashAttribute("error", "授权失效,请重新授权登录");
            return WebConstants.REDIRECT_PREFIX + WebConstants.LOGIN_PAGE;
        }
        try {
            String gitHubUserId = GitHubUtil.accessGitHubUserId(gitAccessToken);
            //1.查询该gitHubUserId是否关联过用户
            ResponseDataEntity<UserEntity> userEntityResponse = memberGitHubService.queryUserByGitUserId(gitHubUserId);
            if (ResponseUtil.checkResponseOk(userEntityResponse)) {
                //关联过直接登录
                return loginByGitUserId(gitHubUserId, gitAccessToken, resp, redirectAttributes);
            }
            //2.封装用户实体
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            ResponseDataEntity<Map<String, String>> mapResponse = memberCommService.login(userEntity);
            if (!mapResponse.getResCode().equals(HttpStatus.OK.value())) {
                //2.登录失败 重定向至关联页面 并显示错误信息
                redirectAttributes.addFlashAttribute("error", mapResponse.getResMsg());
                return WebConstants.REDIRECT_PREFIX + WebConstants.RELATION_PAGE;
            }
            //3.登录成功后绑定git账户,并清除gitAccessToken
            userEntity.setGithubUserId(gitHubUserId);
            ResponseDataEntity<Object> responseData = memberGitHubService.bindGitUserId(userEntity);
            if (!ResponseUtil.checkResponseOk(responseData)) {
                throw new FrozenCommException("绑定git账户失败");
            }
            clearGitAccessTokenCookie(resp);
            return LoginUtil.addLoginToken(resp, mapResponse, redirectAttributes);
        } catch (Exception e) {
            log.error("授权失败", e);
            redirectAttributes.addFlashAttribute("error", "授权失败,请重新授权登录");
            return WebConstants.REDIRECT_PREFIX + WebConstants.LOGIN_PAGE;
        }
    }

    /**
     * <description> 清空gitHubAccessToken的cookie值 </description>
     *
     * @param resp :
     * @author : lw
     * @date : 2020/4/13 13:23
     */
    private void clearGitAccessTokenCookie(HttpServletResponse resp) {
        Cookie cookie = new Cookie(WebConstants.GIT_ACCESS_TOKEN, "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
