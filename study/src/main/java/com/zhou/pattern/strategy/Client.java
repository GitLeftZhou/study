package com.zhou.pattern.strategy;

public class Client {

    public static void main(String[] args) {
        // 获得具体策略可以使用配置文件，工厂模式等动态处理
        Strategy s = new NomalCustomerStrategy();
        Context context = new Context(s);
        context.getPrice(1800.0);
    }
}
