package com.zhou.pattern.proxy;

public class Star implements Concert {
    @Override
    public void confer() {
        System.out.println("Star confer");
    }

    @Override
    public void signContract() {
        System.out.println("Star signContract");

    }

    @Override
    public void sing() {
        System.out.println("Star sing");

    }

    @Override
    public void collectMoney() {
        System.out.println("Star collectMoney");

    }
}
