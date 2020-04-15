package com.frozen.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <program> shopparent </program>
 * <description> 日志接口aop拦截类 </description>
 *
 * @author : lw
 * @date : 2020-03-25 21:10
 **/
@Aspect
@Component
@Slf4j
public class LogAspectAdvice {
    /**
     * <description> 申明一个切点,注解里execution表达式 </description>
     * @author : lw
     * @date : 2020/3/25 21:12
     */
    @Pointcut("execution( public * com.frozen.*.service..*.*(..))")
    private void controllerAspect(){
    }

    @Before(value = "controllerAspect()")
    public void logMethodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("=start==============请求内容==============start=");
        try {
            log.info("访问接口URL"+request.getRequestURL().toString());
            log.info("请求方式"+request.getMethod());
            log.info("请求类方法"+joinPoint.getSignature());
            log.info("请求方法参数"+ Arrays.toString(joinPoint.getArgs()));
        }catch (Exception e){
            log.error("###LogAspectAspect.class logMethodBefore() error###",e);
        }
        log.info("=end==============请求内容==============end=");
    }

    @AfterReturning(value="controllerAspect()",returning = "obj")
    public void logMethodReturning(Object obj){
        log.info("-start-------------返回内容---------------start-");
        try {
            log.info("Response内容:" + JSONObject.toJSONString(obj));
        } catch (Exception e) {
            log.error("###LogAspectServiceApi.class logMethodReturning() ### ERROR:", e);
        }
        log.info("-end-------------返回内容---------------end-");
    }
}
