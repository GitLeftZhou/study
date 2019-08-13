package com.zhou.spring.configuration;

import com.zhou.vo.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

/**
 * Profile:
 *      Spring提供的可以根据当前环境冬天的激活和切换一系列组件的功能
 * e.g
 *      开发环境/测试环境/生成环境
 *      切换数据源
 *
 * 类中声明了profile,以类声明为准
 * 没有声明profile的组件，任何情况下都可以生效
 *      (在类上声明了profile,方法没有声明,方法不能生效)
 *
 * 激活profile环境参数的方式
 *      1.在java虚拟机的运行时命令中加入-Dspring.profiles.active=profile_name
 *      2.在代码中设置容器的环境变量
 */
@Profile("dev")
@PropertySource("classpath:/person.properties")
@Configuration
public class MainConfigOfProfile {

    //展示使用配置文件注入值
    @Value("${person.name.test}")
    private String nameTest;

    @Profile("test")
    @Bean("personTest")
    public Person personTest(){
        return new Person(nameTest, "0");
    }

    //展示使用配置文件注入值
    @Profile("dev")
    @Bean("personDev")
    public Person personDev(@Value("${person.name.dev}")String name){
        return new Person(name, "1");
    }

    @Profile("prod")
    @Bean("personProd")
    public Person personProd(){
        return new Person("prod", "2");
    }

    @Bean("personAny")
    public Person personAny(){
        return new Person("any", "3");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext();
        //设置环境参数,可以设置多个
        applicationContext.getEnvironment().setActiveProfiles("dev","test");
        //注册配置类
        applicationContext.register(MainConfigOfProfile.class);
        //这一步必须，加载配置类中声明的类
        applicationContext.refresh();

        Map<String, Person> beansMap = applicationContext.getBeansOfType(Person.class);
        beansMap.forEach((key,value)->{
            System.out.println(key+":"+value);
        });

    }

}
