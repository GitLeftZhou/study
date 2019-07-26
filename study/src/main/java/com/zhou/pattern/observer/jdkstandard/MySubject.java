package com.zhou.pattern.observer.jdkstandard;

import java.util.Observable;

// Observable 被观察者
public class MySubject extends Observable {

    public void setState(int state) {
        this.state = state;
        super.setChanged();//表示值已经变化
        super.notifyObservers(state);//通知观察者
    }

    public int getState() {
        return state;
    }

    private int state;


}
