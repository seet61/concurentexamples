package com.sbt.javaschool.concurrentexamples;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static long timeTask(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGame = new CountDownLatch(1);
        final CountDownLatch endGame = new CountDownLatch(nThreads);
        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        startGame.await();
                        System.out.println("Start game await");
                        try {
                            task.run();
                        } finally {
                            endGame.countDown();
                        }
                    } catch (InterruptedException ignored) {

                    }
                }
            }, String.valueOf(i));
            t.start();
        }
        Thread.sleep(5000);
        long start = System.currentTimeMillis();
        startGame.countDown();
        endGame.await();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        try {
            System.out.println(timeTask(10, new Runnable() {
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep((long) (Math.random()*1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
