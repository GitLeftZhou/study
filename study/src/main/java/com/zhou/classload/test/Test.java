package com.zhou.classload.test;


import com.zhou.classload.interfaces.Demo;
import com.zhou.classload.core.ClassLoaderFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class Test implements Runnable {

    public static void main(String[] args) {
        new Thread(new Test()).start();
        new Thread(new DeamonThread()).start();
    }

    @Override
    public void run() {
        Class<?> demoClass;
        Map<String, String> moduleInfo = new HashMap<>();
        moduleInfo.put("ma", "com\\zhou\\classload\\module\\ma");
        try {
            while (true) {
                //load 模块入口类
                demoClass = ClassLoaderFactory.getClassLoaderFactory("D:\\WORK\\study", moduleInfo)
                        .loadClass("com.zhou.classload.module.ma.DemoA");
                Demo demo = (Demo) demoClass.getDeclaredConstructor().newInstance();
                demo.load();
                Thread.sleep(4000);
            }

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InterruptedException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
