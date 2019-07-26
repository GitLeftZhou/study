package com.zhou.java8.lambdas;

import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

/**
 * Lambda 表达式语法
 * 1. (T...t) -> {语句1;语句2;...语句N;}
 *    用lambda符号->分隔，左侧为函数式接口参数列表，右侧为方法体
 *    参数列表的数据类型可以忽略
 *    若参数个数只有一个，则可以忽略()
 *    若方法体只有一句，则可以忽略return 和 {}
 *
 * 2. 函数式接口：一个类中有且只有一个抽象方法
 *    可以在类上使用@FuntionalInterface注解，通知编译器进行检查,防止其他人修改
 *
 * 3. 四大内置函数式接口
 *    Consumer<T> void accept(T t) 消费型接口
 *    Supplier<T> T get() 供给型接口
 *    Function<T,R> R apply(T t) 函数型接口
 *    Predicate<T> boolean test(T t) 断言型接口,用做判断
 *
 * 4. 方法引用:若Lambda体中的内容有方法已经实现了，我们可以使用“方法引用”
 *    可以理解为Lambda表达式的另外一种表现形式
 *    a) 对象::实例方法名
 *    b) 类::静态方法名
 *    c) 类::实例方法名
 *
 * 5. 构造器引用:
 *    ClassName::new
 *
 * 6. 数组引用:
 *    Type::new
 */
public class LambdaDemo2 {

    @Test
    public void test01(){
        //语法格式一:  无参 -> 无返回值
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.print("Hello ");
                System.out.println("World");
            }
        };
        r1.run();
        System.out.println("==========================");
        Runnable r2 = () -> {
            System.out.print("Hello ");
            System.out.println("Lambda");
        };
        r2.run();
    }

    @Test
    public void test02(){
        //这个接口很有用！
        // 例如在自定义的功能中做一个外部的未知功能接口，则可以使用aonsumer.accept()
        // 在实际使用时，实时的注入具体实现
        Consumer<String> con = (str) -> {
            //这里打印一句话，实际可以做任何事
            System.out.println(str);
        };
        con.accept("Hello Lambda");

        Employee emp = new Employee("张三", 18);
        Consumer<Employee> consumer = (employee) -> {
            //这里打印一句话，实际可以做任何事
            employee.setAge(100);
//            System.out.println(employee);
        };
        consumer.accept(emp);
        System.out.println(emp);
    }

    @Test
    public void test03(){
        Integer bb = operation(100,i -> i*i);
        System.out.println(bb);

        String aa = operation("abccdewf", str -> str.toUpperCase());
        System.out.println(aa);
    }

    private Integer operation(Integer a, MyFunction<Integer> mf){
        return mf.calculate(a);
    }

    private String operation(String str, MyFunction<String> mf){
        return mf.calculate(str);
    }



}
