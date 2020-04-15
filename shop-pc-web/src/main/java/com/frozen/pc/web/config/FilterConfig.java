package com.frozen.pc.web.config;

import com.frozen.pc.web.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * <program> shop-parent </program>
 * <description> Filter配置类 </description>
 *
 * @author : lw
 * @date : 2020-04-01 11:34
 **/
@Configuration
public class FilterConfig {
    @Autowired
    private LoginFilter loginFilter;
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(loginFilter);
        registrationBean.addInitParameter("targetFilterLifecycle","true");
        registrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,register,login,err,relation");
        registrationBean.addUrlPatterns("/private/*");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return registrationBean;
    }
}
