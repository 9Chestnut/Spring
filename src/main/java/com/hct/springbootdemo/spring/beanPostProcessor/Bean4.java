package com.hct.springbootdemo.spring.beanPostProcessor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: 9Chestnut
 * @Date: 2022年09月02日 00:04
 * @Description:
 */
//@ConfigurationProperties(prefix = "java")
public class Bean4 {

    private String home;
    private String version;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Bean4{" +
                "home='" + home + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}

