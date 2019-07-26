package com.zhou.pattern.observer.jdkstandard;

import java.util.Observable;
import java.util.Observer;

public class ObserverA implements Observer {

    public int getMyState() {
        return myState;
    }

    private int myState;



    @Override
    public void update(Observable o, Object arg) {
        myState = ((MySubject) o).getState();

    }
}
