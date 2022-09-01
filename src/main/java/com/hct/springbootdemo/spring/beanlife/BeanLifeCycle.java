package com.hct.springbootdemo.spring.beanlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 17:39
 * @Description:
 * Bean的生命周期
 */
@SpringBootApplication
public class BeanLifeCycle {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BeanLifeCycle.class, args);
        // 为了测试销毁
        context.close();
    }
}
