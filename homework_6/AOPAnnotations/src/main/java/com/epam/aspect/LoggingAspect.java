package com.epam.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@annotation(com.epam.annotation.Logged)")
    public void loggedPointcut() {

    }

    @Before("loggedPointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("-------------BEFORE EXECUTION-----------------------------");
        System.out.println("On object of type: " + declaringTypeName);
        System.out.println("Executed method: " + name);
        System.out.println("With arguments: ");
        if (args.length != 0) {
            for (Object arg : args) {
                System.out.println("    -> " + arg);
            }
        } else {
            System.out.println("    -> Method has no arguments!");
        }
    }

    @AfterReturning("loggedPointcut()")
    public void afterReturningAdvice() {
        System.out.println("-------------AFTER RETURNING------------------------------");
    }

}
