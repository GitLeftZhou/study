package com.zhou.pattern.proxy;

/**
 * 静态代理
 * 1.代理类与被代理实现相同的方法
 * 2.代理类持有被代理的引用
 * 3.主要业务逻辑使用被代理类的业务逻辑
 */
public class StarProxy implements Concert {

    private Concert star;

    public StarProxy(Concert star) {
        this.star = star;
    }

    @Override
    public void confer() {
        System.out.println("StarProxy confer");
    }

    @Override
    public void signContract() {
        System.out.println("StarProxy signContract");

    }

    @Override
    public void sing() {
        star.sing();
    }

    @Override
    public void collectMoney() {
        System.out.println("StarProxy collectMoney");

    }
}
