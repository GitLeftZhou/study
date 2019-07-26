package com.zhou.pattern.proxy;

public class FakeStar extends Star {

    @Override
    public void sing() {
        System.out.println("我们来玩玩假唱\n\rFake Star sing\n\r其他都一样");
    }
}
