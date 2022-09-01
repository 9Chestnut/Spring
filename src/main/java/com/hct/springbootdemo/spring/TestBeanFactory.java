package com.hct.springbootdemo.spring;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 11:29
 * @Description:
 *  知识点：
 *  beanFactory 不会做的事：
 *      1、不会主动调用 BeanFactory 后置处理器
 *      2、不会主动添加 Bean 后置处理器
 *      3、不会主动初始化单例
 *      4、不会解析 #{} 与 ${}
 *  bean 后处理器会有排序的逻辑
 *      Autowired 在源码中先处理的
 *
 */
@Slf4j
public class TestBeanFactory {

    public static void main(String[] args) {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // bean的定义（class，scope，初始化，销毁等）
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        // bean 注册
        beanFactory.registerBeanDefinition("congif", beanDefinition);

        /**
         * 给 beanFactory 添加一些常用的后置处理器（包括 beanFactory 和 bean 的后处理器
         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor   处理Configuration注解的处理器
         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor   处理 @Autowired
         * org.springframework.context.annotation.internalCommonAnnotationProcessor     处理 @Resource
         * org.springframework.context.event.internalEventListenerProcessor
         * org.springframework.context.event.internalEventListenerFactory
         */
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);

        // 拿到 beanFactory 中的所有后置处理器
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
            // 每个后置处理器都去执行 beanFactory
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        });

        /**
         *  Bean的后置处理器，针对bean的生命周期各个阶段提供扩展，例如 @Autowired、@Resource
         *  把 Bean 的后置处理器添加到 BeanFactory中
         */
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);


        // 展示 beanFactory 中定义的bean
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        // 提前准备好所有的单例 Bean 不会再被懒加载
        beanFactory.preInstantiateSingletons();

        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

    }


    @Configuration // 需要 BeanFactory 的后置处理器去处理
    static class Config{
        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean1 {
        private static final Logger log = LoggerFactory.getLogger(Bean1.class);

        public Bean1() {
            log.info("构造 Bean1()");
        }

        @Autowired   // 需要使用 Bean 的后置处理器去处理
        private Bean2 bean2;

        public Bean2 getBean2() {
            return bean2;
        }
    }

    static class Bean2 {
        private static final Logger log = LoggerFactory.getLogger(Bean2.class);

        public Bean2() {
            log.info("构造 Bean2（）");
        }
    }
}
