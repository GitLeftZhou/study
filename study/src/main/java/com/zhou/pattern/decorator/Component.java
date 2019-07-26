package com.zhou.pattern.decorator;

public class Component implements IComponent {
    @Override
    public void function() {
        System.out.println("原始功能");
    }
}
