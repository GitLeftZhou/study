package com.zhou.pattern.strategy;

public class NomalCustomerStrategy implements Strategy {
    @Override
    public double getPrice(Double standardPrice) {
        System.out.println("普通客户85折");
        return 0.85*standardPrice;
    }
}
