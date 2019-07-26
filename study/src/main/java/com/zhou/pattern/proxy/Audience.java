package com.zhou.pattern.proxy;

import java.lang.reflect.Proxy;

public class Audience {

    /**
     * 静态代理
     */
    public static void testStatic(){
        Concert concert = new Star();
        Concert proxy = new StarProxy(concert);
        proxy.confer();
        proxy.signContract();
        proxy.sing();
        proxy.collectMoney();
    }

    /**
     *   做AOP 做类的动态访问控制
     */
    public static void testDynamic(){
        Concert star = new Star();
        ConcertHandler handler = new ConcertHandler(star);
        Concert proxy = (Concert) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{Concert.class},handler);
        proxy.signContract();
        proxy.sing();
    }

    /**
     * 这个是娱乐  做个假唱  模仿秀
     */
    public static void testDynamic2(){
        Concert star = new Star();
        Concert fstar = new FakeStar();
        ConcertHandler2 handler = new ConcertHandler2(star,fstar);
        Concert proxy = (Concert) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),new Class[]{Concert.class},handler);
        proxy.signContract();
        proxy.sing();
    }


    public static void main(String[] args) {
//        Audience.testStatic();
//        Audience.testDynamic();
        Audience.testDynamic2();
    }

}
