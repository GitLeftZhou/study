package com.zhou.pattern.decorator;
/**
 * 桥接模式和装饰器模式都可以解决继承复杂，子类多的问题
 * 桥接模式和装饰器模式的区别
 * 在类本身不稳定，具有多个变化纬度时，使用桥接
 * 在类本身稳定，但是需要动态增加功能（而非属性），时候用装饰器模式
 * 装饰器关键字（decorator  wrapper）
 */
public class Client {
    public static void main(String[] args) {
        IComponent com = new Component();
        EnhanceComponent1 ecom = new EnhanceComponent1(com);
        ecom.function();
        System.out.println("---------------------------");
        EnhanceComponent2 ecom2 = new EnhanceComponent2(com);
        ecom2.function();
        System.out.println("---------------------------");
        EnhanceComponent2 ecom3 = new EnhanceComponent2(ecom);
        ecom3.function();
    }
}
