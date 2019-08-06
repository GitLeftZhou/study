package com.zhou.spring;

import com.zhou.spring.configuration.MainConfig;
import com.zhou.spring.configuration.MainConfig2;
import com.zhou.vo.Person;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class SpringTest {
    @Test
    public void test01(){
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(MainConfig.class);
        Person person = applicationContext.getBean("person",Person.class);
        System.out.println(person);

        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }
    @Test
    public void test02(){
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(MainConfig2.class);

        Map<String, Person> personMap = applicationContext.getBeansOfType(Person.class);
        personMap.forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });

        //获取环境变量
        Environment environment = applicationContext.getEnvironment();
        System.out.println(environment.getProperty("os.name"));

    }

    @Test
    public void test03(){
        ApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(MainConfig.class);
        Object bean1 = applicationContext.getBean("mySpringFactoryBean");
        System.out.println("SpringTest.test03--->"+bean1.getClass());
        Object bean2 = applicationContext.getBean("&mySpringFactoryBean");
        System.out.println("SpringTest.test03--->"+bean2.getClass());
    }

}
