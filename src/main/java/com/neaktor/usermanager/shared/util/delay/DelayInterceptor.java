package com.neaktor.usermanager.shared.util.delay;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Random;

@Aspect
@Component
public class DelayInterceptor {

    @Around("execution(* com.neaktor.usermanager.service.impl.*.*(..))")
    public Object delayMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Thread.sleep(new Random().nextInt(5000) + 5000);
        return joinPoint.proceed();
    }
}
