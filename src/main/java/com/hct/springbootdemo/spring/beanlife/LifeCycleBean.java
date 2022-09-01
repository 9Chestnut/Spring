package com.hct.springbootdemo.spring.beanlife;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月01日 17:44
 * @Description:
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifeCycleBean {

    private static final Logger log = LoggerFactory.getLogger(LifeCycleBean.class);

    public LifeCycleBean() {
        log.info("构造");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME} ") String home){
        log.info("依赖注入：{}", home);
    }

    @PostConstruct
    public void init(){
        log.info("初始化");
    }

    @PreDestroy
    public void destroy() {
        log.info("销毁");
    }
}
