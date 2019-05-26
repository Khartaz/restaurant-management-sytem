package com.restaurant.management.config;

import com.restaurant.management.web.request.account.LoginRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        LOGGER.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }

    @After("@annotation(LogLogin)" + "&& args(request)")
    public void logLogin(LoginRequest request) {
        LOGGER.info("Login attempt as: " + request.getEmail() + " password " + request.getPassword());
    }
}
