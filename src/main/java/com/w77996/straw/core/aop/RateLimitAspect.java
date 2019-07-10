package com.w77996.straw.core.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.w77996.straw.core.annotation.Limiter;
import com.w77996.straw.core.result.Result;
import com.w77996.straw.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

/**
 * @description: 限流AOP
 * @author: straw
 **/
@Aspect
@Component
@Slf4j
public class RateLimitAspect {

    private RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    /**
     * 定义切点
     * 1、通过扫包切入
     * 2、带有指定注解切入
     */
    @Pointcut("@annotation(com.w77996.straw.core.annotation.Limiter)")
    public void checkPointcut() {
    }

    @ResponseBody
    @Around(value = "checkPointcut()")
    public Object aroundNotice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("拦截到了{}方法...", pjp.getSignature().getName());
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取目标方法
        Method targetMethod = methodSignature.getMethod();
        if (targetMethod.isAnnotationPresent(Limiter.class)) {
            //获取目标方法的@Limiter注解
            Limiter limiter = targetMethod.getAnnotation(Limiter.class);
            rateLimiter.setRate(limiter.perSecond());
            if (!rateLimiter.tryAcquire(limiter.timeOut(), limiter.timeOutUnit())) {
                log.info("rateLimiter lock");
                return Result.error(ResultCode.BUSY);
            }
        }
        return pjp.proceed();
    }
}
