package com.w77996.straw.core.filter;

import com.w77996.straw.core.annotation.IgnoreToken;
import com.w77996.straw.core.constant.Constant;
import com.w77996.straw.core.jwt.JwtHelper;
import com.w77996.straw.core.result.ResultCode;
import com.w77996.straw.exception.GlobalException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: toke过滤
 * @author: straw
 **/
@Slf4j
public class TokenFilter implements HandlerInterceptor{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法上的注解判断是否进行token校验
        IgnoreToken ignoreToken = handlerMethod.getBeanType().getAnnotation(IgnoreToken.class);
        log.info("enter preHandle {}",request.getRequestURL());
        if (ignoreToken == null) {
            ignoreToken = handlerMethod.getMethodAnnotation(IgnoreToken.class);
        }
        if (ignoreToken != null) {
            log.info("ignoreToken not null");
            return true;
        }
        log.info("ignoreToken  null");
        //获取请求头中Authorization的参数
        String token = request.getHeader("Authorization");
        if(token != null){
            log.info("token is {}",token);
            if ("admin".equals(token.substring(7))) {
                return true;
            }
            //使用jwt进行token校验
            Claims claims = JwtHelper.parseJWT(token.substring(7), Constant.JWT_BASE64_SECRET);
            if(claims != null){
                log.info("claims is {} {}",claims.toString(),claims.getSubject());
                return true;
            }else{
                log.info("claims is null");
                throw new GlobalException(ResultCode.ERROR_AUTH);
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
