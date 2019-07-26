package com.zhou.classload.module.ma;

import com.zhou.classload.interfaces.Demo;


public class DemoA implements Demo {
//    public static void main(String[] args) {
//        new DemoA().say();
//    }

    @Override
    public void load() {
        System.out.print("demoa say : this is demoa loading.....");
        System.out.println("demoa is "+this.getClass().getClassLoader());
        MyDemo myDemo = new MyDemo();
        System.out.println("mydemo is "+myDemo.getClass().getClassLoader());
        myDemo.excute();
    }
}
