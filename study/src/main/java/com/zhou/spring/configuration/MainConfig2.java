package com.zhou.spring.configuration;

import com.zhou.spring.configuration.condition.LinuxEnvironment;
import com.zhou.spring.configuration.condition.WindowsEnvironment;
import com.zhou.vo.Person;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * Configuration 声明这是一个配置类
 * Conditional 满足当前条件，当前配置类中的配置信息才生效
 *
 *
 */
@Conditional(LinuxEnvironment.class)
@Configuration
public class MainConfig2 {

    /**
     * 默认单实例
     * bean id 使用两种方式 注解传入，方法名
     * 使用Scope注解，声明生命周期
     * 	 @see ConfigurableBeanFactory#SCOPE_PROTOTYPE 多实例
     * 	 @see ConfigurableBeanFactory#SCOPE_SINGLETON
     * 	    单实例 默认值 IOC容器创建时创建对象
     * 	    懒加载 使用Lazy注解 第一次使用时创建对象
     * 	 @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST
     * 	    一次请求一个实例
     * 	 @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION
     * 	    同一个session一个实例
     *
     *
     * @return
     */
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Bean("person")
    public Person personAny(){

        return new Person("lisi","22");
    }


    /**
     * Conditional({}) 按照一定的条件注册Bean
     * 这个注解可以用在类和方法上，例如可以用来实现不同操作系统环境加载不同的配置类
     */
    @Bean
    @Conditional(WindowsEnvironment.class)
    public Person person01(){
        return new Person("user01","50");

    }
    @Bean
    @Conditional(LinuxEnvironment.class)
    public Person person02(){
        return new Person("user02","48");

    }
}

