package com.hct.springbootdemo.spring.beanPostProcessor;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 22:36
 * @Description:
 */
public class TestBeanPostProcessor {
    public static void main(String[] args) {

        // GenericApplicationContext是一个【干净】的容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 用原始的方法注册三个 bean
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        // ??????????????什么作用？？？？？？？？？
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());

        /**
         *  解析 @AutoWired的后处理器
         */
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        /**
         * 解析 @Resource  @PostConstruct   @PreDestroy的后处理器
         */
        context.registerBean(CommonAnnotationBeanPostProcessor.class);

        // ConfigurationProperties
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());


        // 初始化容器,执行beanfactory的后处理器，添加bean后处理器，初始化所有单例
        context.refresh();

        // 销毁容器
        context.close();
    }
}
