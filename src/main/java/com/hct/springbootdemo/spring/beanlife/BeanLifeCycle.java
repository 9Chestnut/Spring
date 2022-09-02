package com.hct.springbootdemo.spring.beanlife;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 17:39
 * @Description:
 * Bean的生命周期
 */
@SpringBootApplication
public class BeanLifeCycle {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(BeanLifeCycle.class, args);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));

        context.close();
    }
}
