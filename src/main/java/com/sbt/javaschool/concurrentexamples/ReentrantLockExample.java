package com.sbt.javaschool.concurrentexamples;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    //private static ReentrantLock  lock = new ReentrantLock();
    //справедливая
    private static ReentrantLock  lock = new ReentrantLock(true);

    private static void print(String tag, String p) {
        System.out.println(Thread.currentThread().getName() + " : " + tag + " : " + p);
    }

    private static class AcquireLockRunnable implements Runnable {

        public void run() {
            try {
                int threadNumber = Integer.parseInt(Thread.currentThread().getName());

                print("AcquireLockRunnable", "try lock");
                lock.lock();

                /*
                if (lock.tryLock(1500, TimeUnit.MILLISECONDS)) {
                    print("AcquireLockRunnable", "try lock ok");
                } else {
                    print("AcquireLockRunnable", "try lock nok");
                }
                */
                if (threadNumber < 5) {
                    Thread.sleep(1000);
                } else {
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                print("AcquireLockRunnable", "unlock");
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) {
            Thread t = new Thread(new AcquireLockRunnable(), String.valueOf(i));
            t.start();
        }
    }
}
