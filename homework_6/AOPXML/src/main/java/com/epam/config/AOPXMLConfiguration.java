package com.epam.config;

import com.epam.service.NumberSearcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPXMLConfiguration {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop-config.xml");
        NumberSearcher numberFinder = (NumberSearcher) applicationContext.getBean("numberFinder");
        numberFinder.searchForNumber(100);
    }
}
