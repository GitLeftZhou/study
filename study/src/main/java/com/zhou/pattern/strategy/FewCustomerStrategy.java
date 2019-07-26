package com.zhou.pattern.strategy;

public class FewCustomerStrategy implements Strategy {
    @Override
    public double getPrice(Double standardPrice) {
        System.out.println("小客户不打折");
        return standardPrice;
    }
}
