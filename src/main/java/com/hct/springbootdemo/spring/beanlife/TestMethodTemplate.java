package com.hct.springbootdemo.spring.beanlife;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 22:13
 * @Description:  后处理器的模板方法
 */
public class TestMethodTemplate {
    private static final Logger log = LoggerFactory.getLogger(TestMethodTemplate.class);

    public static void main(String[] args) {

        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(bean -> System.out.println("解析 @Autowired"));
        beanFactory.getBean();

    }

    static class MyBeanFactory{

        private List<BeanPostProcess> processors = new ArrayList<>();

        public void addBeanPostProcessor(BeanPostProcess processor){
            processors.add(processor);
        }

        public Object getBean(){
            Object bean = new Object();
            log.info("构造：" + bean);
            // 要在依赖注入时做增强，改动原有的代码，影响开闭原则
            log.info("依赖注入：" + bean);
            for (BeanPostProcess processor : processors){
                processor.inject(bean);
            }
            log.info("初始化：" + bean);
            return bean;
        }
    }

    static interface BeanPostProcess{
        /**
         * 对依赖注入阶段扩展
         */
        void inject(Object bean);
    }
}
