package com.zhou.pattern.state;

public class FreeState implements State {
    @Override
    public void handle() {
        System.out.println("空房间可以接受预定");
    }
}
