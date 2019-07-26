package com.zhou.pattern.adapter;


public class Client {

    public void test01(Target target){
        target.handleRequest();
    }

    public static void main(String[] args) {
        Client cc = new Client();
        cc.test01(new Adapter());
        cc.test01(new Adapter2(new Adaptee()));

    }
}
