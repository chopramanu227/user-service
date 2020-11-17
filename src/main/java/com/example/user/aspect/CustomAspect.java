package com.example.user.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CustomAspect {

    @Around("@annotation(LogEntryExit)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        log.debug("Execution started for method - {},  inside - {}", method, className);
        Object proceed;
        try{
            proceed= joinPoint.proceed();
        }catch (Exception e){
            log.debug("Exception occurred in executing method - {},  inside - {}", method, className);
            throw e;
        }
        log.debug("Execution completed for method - {},  inside - {}", method, className);
        return proceed;
    }
}
