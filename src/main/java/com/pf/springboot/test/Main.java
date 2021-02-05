package com.pf.springboot.test;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    static final CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException,
            ExecutionException, ParseException {

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService exec = Executors.newFixedThreadPool(availableProcessors);

        List<Future<Date>> results = new ArrayList<>();

        final SimpleDateFormatExample sdf = new SimpleDateFormatExample();
        Callable<Date> parseDateTask = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                Date date = sdf.parseDate("20161118", latch);
                return date;
            }
        };

        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(parseDateTask));
        }

        exec.shutdown();

        /**
         * 查看结果
         */
        for (Future<Date> result : results) {
            System.out.println(result.get());
        }
    }
}