package com.smart.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class tBean {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        String[] str = context.getBeanDefinitionNames();

        for (String string : str) {
            System.out.println("..." + string);
        }


    }
}
