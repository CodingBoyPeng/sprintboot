package com.pf.springboot.entity;

/**
 * @description:
 * @author:Peng
 * @Date:2021/3/30
 */
public class A {
    private String[] status = new String[]{"All", "AK"};

    public String[] getStatus() {
        return status;
    }

    public static void main(String[] args) {
        StringBuilder a;

        for (int i =0; i<500;i++) {
            a = new StringBuilder(30);
            a.append(i);
            System.out.println(a.toString());

        }

    }
}
