package com.zhou.java8.lambdas;

public class FilterByAge implements MyFilter<Employee> {
    @Override
    public boolean check(Employee e) {
        return e.getAge()>25 ? true : false;
    }
}
