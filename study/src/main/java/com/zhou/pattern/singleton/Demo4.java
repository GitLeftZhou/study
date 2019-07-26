package com.zhou.pattern.singleton;

/**
 * 单例模式关键点：线程安全，调用效率，延时加载
 * 枚举实现
 * 1.JVM保证线程安全
 * 2.无锁机制，调用效率高
 * 3.无延时加载，内存资源利用率低
 */
public enum  Demo4 {

    INSTANCE;

    public void operation(){
        System.out.println("do some operation");
    }

}
