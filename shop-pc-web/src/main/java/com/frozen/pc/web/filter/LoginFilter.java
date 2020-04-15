package com.frozen.pc.web.filter;

import com.frozen.member.api.MemberCommService;
import com.frozen.member.bean.UserEntity;
import com.frozen.pc.web.comm.WebConstants;
import com.frozen.pc.web.utils.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * <program> shop-parent </program>
 * <description> 登录过滤器 </description>
 *
 * @author : lw
 * @date : 2020-04-01 10:20
 **/
@Slf4j
@Component
public class LoginFilter implements Filter {

    @Autowired
    private MemberCommService memberCommService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("loginFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        UserEntity user = WebUtil.getLoginUser();
        if (Objects.isNull(user)) {
            //跳转至登录界面
            resp.sendRedirect(WebConstants.LOGIN_PAGE);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
