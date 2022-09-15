package com.hct.springbootdemo.spring.beanFactoryPostProcessor.component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author CandyWall
 * @Date 2022/3/29--12:42
 * @Description
 */
@Slf4j
public class Bean4 {
    public Bean4() {
        log.debug("我被 Spring 管理啦");
    }
}
