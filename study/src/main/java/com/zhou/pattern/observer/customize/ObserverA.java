package com.zhou.pattern.observer.customize;

public class ObserverA implements Observer {

    public int getMyState() {
        return myState;
    }

    private int myState;
    @Override
    public void update(Subject subject) {
        this.myState = subject.getState();
    }
}
