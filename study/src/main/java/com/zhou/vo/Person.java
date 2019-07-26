package com.zhou.vo;

import java.io.Serializable;

public class Person implements Serializable{

    private String name;
    private String age;

    @Override
    public String toString() {
        return "com.zhou.vo.Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
