package com.pf.springboot.test;

import java.util.regex.Pattern;

/**
 * @description:
 * @author:Peng
 * @Date:2021/3/1
 */
public class PatternTest {
    private static final Pattern p = Pattern.compile("\\d+");
    public static void main(String[] args) {
  
        String[] str=p.split("我的QQ是:456sadsad456我的电话是:0532214我的邮箱是:aaa@aaa.com");
        for (String s : str) {
            System.out.println(s);
        }
    }
}
