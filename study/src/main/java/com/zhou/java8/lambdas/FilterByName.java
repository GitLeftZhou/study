package com.zhou.java8.lambdas;

public class FilterByName implements MyFilter<Employee> {
    @Override
    public boolean check(Employee e) {
        return e.getName().startsWith("张") ? true : false;
    }
}
