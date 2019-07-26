package com.zhou.multithreading;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现两个线程依次拿数
 */
public class ThreadDemo {

    private static int cnt = 1;
    @Test
    public void test01(){
        MyThreadByNotify myThread = new MyThreadByNotify();
//        MyThreadByLock myThread = new MyThreadByLock();
        Thread thread1 = new Thread(myThread);
        Thread thread2 = new Thread(myThread);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void test02(){
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(10);
        workQueue.add(() -> System.out.println(Thread.currentThread().getName()+":" + (cnt++)));
        while (workQueue.size()>0){
            Thread worker = null;
            try {
                Runnable ru = workQueue.poll();
                if (null != ru) {
                    worker = new Thread(ru);
                    if (cnt<=10){
                        worker.start();
                        workQueue.put(worker);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(1, 10, 60, TimeUnit.SECONDS, workQueue);
        for (int i = 0; i < 11; i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+":" + (cnt++));
                }
            });
        }*/
    }


    /**
     * 使用 notify wait
     */
    class MyThreadByNotify implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (this){
                    this.notify();
                    if(cnt<=10){
                        System.out.println(Thread.currentThread().getName()+":" + cnt++);
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                        }
                    }else {
                        return;
                    }
                }
            }
        }
    }

    /**
     * 使用Lock框架,貌似不能实现
     */
    class MyThreadByLock implements Runnable{
        Lock lock = new ReentrantLock();
        @Override
        public void run() {
            while (true){
                //lock.lock();  使用tryLock可以观测到锁争用情况 lock是直接阻塞，无法直观的观测
                if (lock.tryLock()) {
                    System.out.println(Thread.currentThread().getName()+" try to get lock success");
                    try {
                        if (cnt <= 10) {
                            System.out.println(Thread.currentThread().getName() + ":" + cnt++);
                        } else {
                            return;
                        }
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName()+" try to get lock failure");
                }
            }
        }
    }

}
