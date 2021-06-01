package com.pf.springboot.test;

import org.springframework.transaction.annotation.Transactional;

public class Test06 {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        Task task = new Task();
        new Thread(task).start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(task).start();

    }
    @Transactional(rollbackFor = Exception.class)
    static class Task implements Runnable{
        @Override
        public void run() {
            Long tim = threadLocal.get();
            if (tim == null) {
                long l = System.currentTimeMillis();
                threadLocal.set(l);
                System.out.println("当前线程：" +  Thread.currentThread().getName() + "，当前值：" + threadLocal.get());

            }
        }
    }
}

