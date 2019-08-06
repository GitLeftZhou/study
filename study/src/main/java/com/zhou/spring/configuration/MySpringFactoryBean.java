package com.zhou.spring.configuration;

import com.zhou.vo.Person;
import org.springframework.beans.factory.FactoryBean;


public class MySpringFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person("factoryBean","170");
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
