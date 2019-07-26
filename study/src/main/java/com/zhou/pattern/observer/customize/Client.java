package com.zhou.pattern.observer.customize;

public class Client {

    public static void main(String[] args) {

        Subject subject = new Subject();
        ObserverA observer1 = new ObserverA();
        ObserverA observer2 = new ObserverA();
        ObserverA observer3 = new ObserverA();

        subject.registObserver(observer1);
        subject.registObserver(observer2);
        subject.registObserver(observer3);

        subject.setState(500);

        System.out.println(observer1.getMyState());
        System.out.println(observer2.getMyState());
        System.out.println(observer3.getMyState());


    }
}
