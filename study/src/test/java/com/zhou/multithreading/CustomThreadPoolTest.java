package com.zhou.multithreading;

import org.junit.Test;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CustomThreadPoolTest {

    @Test
    public void submit() {
    }

    @Test
    public void execute() {
        BlockingQueue<Runnable> taskDeque = new LinkedBlockingQueue<Runnable>(20);
        Notify notify = null;
        CustomThreadPool pool = new CustomThreadPool(2, 5,
                60, TimeUnit.SECONDS, taskDeque, notify);

        for (int i = 0; i < 50; i++) {
            final int idx = i;
            pool.execute(new Runnable() {
                public String name = String.format("task %d",idx);
                @Override
                public void run() {
                    try {
                        Thread.currentThread().setName(name);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    System.out.println(name);
                }
            });
        }
        pool.mainNotify();
        System.out.println("main thread is completed.");

    }

    public void test02(){
        System.out.println("");
        System.out.println("");
    }
}