package com.zhou.pattern.state;

public class BookedState implements State {
    @Override
    public void handle() {
        System.out.println("房间已经预定,别人不能定");
    }
}
