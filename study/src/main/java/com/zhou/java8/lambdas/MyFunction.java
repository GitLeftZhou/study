package com.zhou.java8.lambdas;
@FunctionalInterface
public interface MyFunction<T> {
    T calculate(T t);
}
