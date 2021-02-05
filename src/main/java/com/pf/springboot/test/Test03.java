package com.pf.springboot.test;

import com.pf.springboot.entity.Company;

/**
 * @description:
 * @author:Peng
 * @Date:2021/1/25
 */
public class Test03 {
    public static void main(String[] args) {
        Test03 test03 = new Test03();
        Integer i = 3;
        test03.getInumAdd2(i);
        test03.getInumAdd3(i);

    }

    public void getInumAdd2(Integer i){
        i = i + 2;
        System.out.println(i);
    }


    public void getInumAdd3(Integer i){
        i = i + 3;
        System.out.println(i);
    }

}
