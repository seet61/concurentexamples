package com.sbt.javaschool.concurrentexamples;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyCallable implements Callable<String> {

    long timeSleep;

    MyCallable (long timeSleep) {
        this.timeSleep = timeSleep;
    }

    public String call() throws Exception {
        Thread.sleep(timeSleep);
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<String>> list = new ArrayList<Future<String>>();
        Callable<String> callable = new MyCallable(1000);
        for (int i = 0; i < 100; i++) {
            Future<String> future = executorService.submit(callable);
            list.add(future);
        }

        for (Future<String> fut: list) {
            try {
                System.out.println(fut.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

    }
}
