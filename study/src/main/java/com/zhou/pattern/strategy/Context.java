package com.zhou.pattern.strategy;

public class Context {

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public double getPrice(double price){
        double newPrice = strategy.getPrice(price);
        System.out.println("获得的报价是"+newPrice);
        return newPrice;
    }

}
