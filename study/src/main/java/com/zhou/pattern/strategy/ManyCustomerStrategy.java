package com.zhou.pattern.strategy;

public class ManyCustomerStrategy implements Strategy {
    @Override
    public double getPrice(Double standardPrice) {
        System.out.println("大客户8折");
        return 0.8*standardPrice;
    }
}
