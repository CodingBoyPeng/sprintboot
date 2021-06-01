package com.pf.springboot.test;

/**
 * @author ：codingboypeng
 * @description：TODO
 * @date ：2021/5/27 15:14
 */
public class TestStartOrRun {
    public static void main(String[] args) {
        Task task = new Task();
        Thread start = new Thread(task, "start线程");
        start.start();
        System.out.println("ping");

//        Task task2 = new Task();
//        Thread run = new Thread(task2, "rum线程");
//        run.run();
//        System.out.println("run ping");
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("");
//            }
//        }, "嘿嘿嘿");
    }
}

class Task implements Runnable{

    @Override
    public void run() {
        System.out.println("执行咯，当前线程名称: " + Thread.currentThread().getName());
    }
}