package com.epam.config;


import com.epam.service.NumberSearcher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.epam")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopAnnotationConfig {
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AopAnnotationConfig.class);
        context.refresh();
        NumberSearcher numberSearcher = context.getBean(NumberSearcher.class);
        System.out.println(numberSearcher.searchForNumber(100));
    }
}
