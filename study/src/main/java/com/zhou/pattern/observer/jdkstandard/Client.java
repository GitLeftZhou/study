package com.zhou.pattern.observer.jdkstandard;

/**
 * 适用于聊天软件：A--->服务器--->广播（一个人也是特定的广播）
 * 多人对战游戏：将一个玩家的实时数据更新到服务器，服务器广播到相关玩家
 * 订阅服务
 *
 * 当前案例为类之间设置  set 方法可以做成网络调用
 */
public class Client {

    public static void main(String[] args) {

        //主题
        MySubject mySubject = new MySubject();

        // 实例化具体的监听器
        ObserverA obs1 = new ObserverA();
        ObserverA obs2 = new ObserverA();
        ObserverA obs3 = new ObserverA();

        //将监听器注册到主题
        mySubject.addObserver(obs1);
        mySubject.addObserver(obs2);
        mySubject.addObserver(obs3);

        //主题发生变化
        mySubject.setState(300);

        //监听器获得相应的变化
        System.out.println(obs1.getMyState());
        System.out.println(obs2.getMyState());
        System.out.println(obs3.getMyState());
    }
}
