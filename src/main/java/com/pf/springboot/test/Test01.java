package com.pf.springboot.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author:Peng
 * @Date:2021/1/13
 */
public class Test01 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>() {{
            this.add(1);
            this.add(2);
            this.add(3);
            this.add(4);
            this.add(5);
            this.add(6);

        }};
        List<Integer> integers = list.subList(1, 5);
        System.out.println(integers);
    }
}
