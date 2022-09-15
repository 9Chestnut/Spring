package com.hct.springbootdemo.spring.beanFactoryPostProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月15日 15:42
 * @Description:
 */
@Slf4j
public class TestBeanFactoryPostProcessor {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("config", Config.class);
        // 需要加入BeanFactory后处理器才能扫描到其他的
        context.registerBean(ConfigurationClassPostProcessor.class);

        // 初始化容器
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        // 销毁容器
        context.close();
    }
}
