package com.epam.aspect;

import org.aspectj.lang.JoinPoint;

public class LoggingAspect {

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

    public void afterReturningAdvice() {
        System.out.println("-------------AFTER RETURNING------------------------------");
    }

}
