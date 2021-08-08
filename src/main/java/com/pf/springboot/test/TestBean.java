package com.pf.springboot.test;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：codingboypeng
 * @description：TODO
 * @date ：2021/5/19 17:44
 */
@Configuration
public class TestBean implements BeanNameAware {
    private String name;
    private String beanName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeanName() {
        return beanName;
    }
    public static void run(){
        System.out.println("run run run");
    }
    public TestBean() {
        this.beanName = "123";
//        System.out.println("TestBean方法无参构造调用了");
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
