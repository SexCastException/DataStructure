package com.huazai.learning.date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author pyh
 * @email pyh@efala.com
 * @date 2019/12/19 14:47:23
 */
public class TestSimpleDateFormat1 {
    public static void main(String[] args) throws Exception {
        //解决多线程安全问题
        Callable<Date> task = new Callable<Date>() {

            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convert("20161121");
            }

        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        List<Future<Date>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }
}
