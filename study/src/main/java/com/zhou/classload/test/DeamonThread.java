package com.zhou.classload.test;

import com.zhou.classload.core.ClassLoaderFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DeamonThread implements Runnable {
    public static void main(String[] args) {
        new Thread(new DeamonThread()).start();
    }
    @Override
    public void run() {
        while (true){
            Map<String,String> moduleInfo = new HashMap<>();
            moduleInfo.put("ma","com\\zhou\\classload\\module\\ma");
            System.out.println("输入 模块名 重新加载模块" );
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            System.out.println("输入的值为："+input);
            if (moduleInfo.containsKey(input)){
                ClassLoaderFactory classLoaderFactory
                        = ClassLoaderFactory.getClassLoaderFactory("D:\\WORK\\study",moduleInfo);
                classLoaderFactory.removeClassLoader(input);
                System.out.println("模块"+input+"卸载完成");
            }
        }
    }
}
