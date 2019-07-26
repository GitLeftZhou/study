package com.zhou.pattern.prototype;

import java.util.Date;

/**
 * 在构造类频繁且较为耗时时，考虑使用原型模式（克隆）
 * javascript中的类的继承 使用prototype实现
 */
public class Demo1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        Date date = new Date(11123468541354L);
        Sheep s1 = new Sheep("s1",date);
        System.out.println(s1.getBirthday());
        Sheep s2 = (Sheep) s1.clone();
        date.setTime(22123468541654L);
        System.out.println(s1.getBirthday());
        System.out.println(s2.getBirthday());
    }
}
