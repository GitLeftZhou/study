package com.zhou.java8.lambdas;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * lambda 使用场景演进
 */
public class LambdaDemo {

    public static List<Employee> employees = Arrays.asList(
            new Employee("张三",18),
            new Employee("张谁",26),
            new Employee("李四",20),
            new Employee("王五",30),
            new Employee("赵六",40),
            new Employee("钱七",50)
    );

    @Test
    public void test01() {
        /*
        使用最原始的方式过滤
         */
        List<Employee> list = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getAge() >= 30) {
                list.add(emp);
            }
        }
        for (Employee emp : list) {
            System.out.println(emp);
        }
        list = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getName().startsWith("张")) {
                list.add(emp);
            }
        }
        for (Employee emp : list) {
            System.out.println(emp);
        }
    }

    @Test
    public void test02(){
         /*
          *  策略模式
         */
        List<Employee> list = filter(employees, new FilterByAge());
        for (Employee emp : list) {
            System.out.println(emp);
        }
        List<Employee> list2 = filter(employees, new FilterByName());
        for (Employee emp : list2) {
            System.out.println(emp);
        }
    }

    @Test
    public void test03(){
        /*
            策略模式，使用匿名内部类
         */
        List<Employee> list = filter(employees, new MyFilter<Employee>() {
            @Override
            public boolean check(Employee e) {
                return e.getAge()>25 ? true : false;
            }
        });
        for (Employee emp : list) {
            System.out.println(emp);
        }
        List<Employee> list2 = filter(employees, new MyFilter<Employee>() {
            @Override
            public boolean check(Employee e) {
                return e.getName().startsWith("张") ? true : false;
            }
        });
        for (Employee emp : list2) {
            System.out.println(emp);
        }
    }

    @Test
    public void test04(){
        /*
         * 使用lambda
         */
        List<Employee> list = filter(employees, e -> e.getAge()>25 ? true : false);
        list.forEach(System.out::println);
        System.out.println("=================================");
        List<Employee> list2 = filter(employees, e -> e.getName().startsWith("张") ? true : false);
        list2.forEach(System.out::println);

    }

    @Test
    public void test05(){
        /*
         * 使用lambda + stream
         */
        employees.stream()
                .filter((e) -> e.getAge()>30)
                .forEach(System.out::println);

    }

    private static List<Employee> filter(List<Employee> employees, MyFilter<Employee> filter){
        List<Employee> list = new ArrayList<>();
        for (Employee emp : employees) {
            if (filter.check(emp)){
                list.add(emp);
            }
        }
        return list;
    }


}
