package com.zhou.pattern.singleton;

/**
 * 单例模式关键点：线程安全，调用效率，延时加载
 * 懒汉式，使用时才初始化，说明懒。
 * 1.JVM保证线程安全
 * 2.锁机制，调用效率低
 * 3.延时加载，内存资源利用率高
 */
public class Demo2 {

    private static Demo2 instance = null;

    private Demo2(){}

    public synchronized static Demo2 getInstance(){
        if(null == instance){
            instance = new Demo2();
        }
        return instance;
    }

}

/**
 * 双重检测 懒汉式，因编译器优化等原因，在少数情况下会产生不可预测的问题
 * 不建议使用
 * 延时加载 调用效率较高
 */
class Demo22 {

    private static Demo22 instance = null;

    private Demo22(){}

    public static Demo22 getInstance(){
        if(null == instance){
            synchronized (Demo22.class){
                if(null == instance){
                    instance = new Demo22();
                }
            }
        }
        return instance;
    }

}
