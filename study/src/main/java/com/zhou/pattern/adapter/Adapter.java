package com.zhou.pattern.adapter;
/**
 *  使用继承+接口 达到适配效果
 *
 */
public class Adapter extends Adaptee implements Target{
    @Override
    public void handleRequest() {
        System.out.println("Adapter is avalible");
        super.request();
    }
}

/**
 *  略微看起来和静态代理有点儿像
 *  静态代理需要和被代理类实现相同的接口，持有目标对象的引用
 */
class Adapter2 implements Target{


    public Adapter2(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    private Adaptee adaptee;

    @Override
    public void handleRequest() {
        System.out.println("Adapter2 is avalible");
        adaptee.request();
    }
}
