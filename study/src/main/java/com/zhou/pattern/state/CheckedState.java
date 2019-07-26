package com.zhou.pattern.state;

public class CheckedState implements State {
    @Override
    public void handle() {
        System.out.println("已入住，请勿打扰");
    }
}
