package com.zhou.pattern.singleton;

/**
 * 单例模式关键点：线程安全，调用效率，延时加载
 * 内部类实现, 建议实现
 * 1.JVM保证线程安全
 * 2.无锁机制，调用效率高
 * 3.延时加载，内存资源利用率高
 */
public class Demo3 {


    private Demo3(){}

    public static Demo3 getInstance(){
        return Demo3Instance.instance;
    }

    private static class Demo3Instance{
        private static final Demo3 instance = new Demo3();
    }
}
