package com.zhou.pattern.observer.customize;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private List<Observer> list = new ArrayList<>();

    public void registObserver(Observer obs){
        list.add(obs);
    }

    public void removeObserver(Observer obs){
        list.remove(obs);
    }

    public void notifyObserver(){
        for (Observer observer : list) {
            observer.update(this);
        }
    }

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        this.notifyObserver();

    }
}
