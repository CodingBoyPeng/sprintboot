package com.pf.springboot.test;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangmengjun
 */
public class SimpleDateFormatExample {

    private static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(
            "yyyyMMdd");

    public Date parseDate(String value, CountDownLatch latch) throws ParseException {
        Date date = new SimpleDateFormat("yyyyMMdd").parse(value);
        latch.countDown();
        return date;
    }
}