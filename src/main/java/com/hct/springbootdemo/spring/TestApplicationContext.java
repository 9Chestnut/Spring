package com.hct.springbootdemo.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 16:31
 * @Description:
 * 学到了什么：
 *     常见的 ApplicationContext 容器实现
 *     内嵌容器 DispatcherServlet 的创建方法、作用
 */
public class TestApplicationContext {
    private static final Logger log = LoggerFactory.getLogger(TestApplicationContext.class);

    public static void main(String[] args) {

//        testClassPathXmlApplicationContext();

        testAnnotationConfigApplicationContext();

        /**
         * ClassPathXmlApplicationContext内部的执行流程
         */
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        log.info("before---读取xml文件之前 beanfactory中的bean的name......");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        log.info("after---读取xml文件之后 beanfactory中的bean的name......");
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        // 指定要读取的xml的位置
        xmlBeanDefinitionReader.loadBeanDefinitions(new ClassPathResource("b01.xml"));
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }


    }

    /**
     * 基于 classpath 下xml格式的配置文件来创建
     */
    private static void testClassPathXmlApplicationContext(){

        ClassPathXmlApplicationContext pathXmlApplicationContext = new ClassPathXmlApplicationContext("b01.xml");

        for (String name : pathXmlApplicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(pathXmlApplicationContext.getBean(Bean2.class).getBean1());

    }

    /**
     * 基于磁盘路径下xml格式的配置文件来创建
     */
    private static void testFileSystemXmlApplicationContext(){
        FileSystemXmlApplicationContext systemXmlApplicationContext = new FileSystemXmlApplicationContext("");
    }

    /**
     * 基于 java 配置类来创建
     */
    private static void testAnnotationConfigApplicationContext(){

        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(Config.class);

        /**
         * 输出结果
         * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
         * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
         * org.springframework.context.annotation.internalCommonAnnotationProcessor
         * org.springframework.context.event.internalEventListenerProcessor
         * org.springframework.context.event.internalEventListenerFactory
         * testApplicationContext.Config
         * bean1
         * bean2
         */
        for (String name : configApplicationContext.getBeanDefinitionNames()) {
            System.out.println(name);
        }

        System.out.println(configApplicationContext.getBean(Bean2.class).getBean1());


    }

    /**
     * 基于 java 配置类来创建，用于 web 环境
     */
    private static void testAnnotationConfigServletWebApplicationContext() {

    }

//    @Configuration
//    static class WebConfig{
//        @Bean
//        public ServletWebServerFactory servletWebServerFactory() {
//            return new TomcatServletWebServerFactory();
//        }
//
//        @Bean
//        public DispatcherServlet dispatcherServlet() {
//            return new DispatcherServlet();
//        }
//
//        @Bean
//        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
//            return new DispatcherServletRegistrationBean(dispatcherServlet(), "/");
//        }
//
//        @Bean
//        public Controller controller() {
//
//        }
//    }



    @Configuration
    static class Config{

        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1());
            return bean2;
        }
    }


    static class Bean1{
    }

    static class Bean2 {

        private Bean1 bean1;

        public void setBean1(Bean1 bean1){
            this.bean1 = bean1;
        }

        public Bean1 getBean1() {
            return bean1;
        }
    }
}
