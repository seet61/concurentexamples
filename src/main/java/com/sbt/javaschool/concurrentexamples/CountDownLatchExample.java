package com.sbt.javaschool.concurrentexamples;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    private static final Set<Integer> set = new HashSet<Integer>();
    public static synchronized void add(Integer i) {set.add(i);}
    public static synchronized void remove(Integer i) {set.remove(i);}
    public static void addTenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            add(r.nextInt());
        System.out.println("DEBUG: added ten elements to " + set);
    }


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
                    /*try {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep((long) (Math.random()*1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/

                    addTenThings();
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
