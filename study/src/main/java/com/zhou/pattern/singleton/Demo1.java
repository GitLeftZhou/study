package com.zhou.pattern.singleton;

/**
 * 单例模式关键点：线程安全，调用效率，延时加载
 * 饿汉式，一来就初始化，说明饿。
 * 最简单实现，对于创建单例开销不大的情况，建议使用此实现
 * 1.JVM保证线程安全
 * 2.无锁机制，调用效率高
 * 3.无延时加载，内存资源利用率低
 */
public class Demo1 {

    private static Demo1 instance = new Demo1();

    private Demo1(){}

    public static Demo1 getInstance(){
        return instance;
    }

}
