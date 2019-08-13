package com.zhou.spring.services;

import com.zhou.spring.dao.PersonDao;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @see org.springframework.beans.factory.config.BeanPostProcessor 更细化的初始化控制
 *      可以实现初始化前，初始化后分别自定义处理逻辑
 */
@Service
public class PersonService implements InitializingBean, DisposableBean {

    /**
     * 一、
     * 属性自动注入 Autowired 默认required=true,装配失败抛出异常;false能装配就装配
     *      step.1:根据类型注入
     *      step.2:根据属性名做为bean id注入
     * 使用注解Qualifier指定的名字做为bean id
     * 装配优先装逼使用注解Primary声明的bean,与Qualifier不能同时使用
     *
     * 二、
     * 也可以使用@Resource(JSR250)@Inject(JSR330) 自动注入,@Inject非jdk实现需要引入依赖javax.inject包
     *
     *
     */
    @Autowired(required = false)
    @Qualifier("personDao")
    private PersonDao personDao;

    @PostConstruct
    public void init01(){
        //此注解基于JSR250标准实现 JKD1.6+
        System.out.println("PersonService.init 使用注解PostConstruct声明此方法为初始化方法");
    }

    @PreDestroy
    public void destroy01(){
        //此注解基于JSR250标准实现 JKD1.6+
        System.out.println("PersonService.destroy 使用注解PreDestroy声明此方法为销毁清理工作");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("PersonService.afterPropertiesSet 实现接口InitializingBean实现初始化");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("PersonService.destroy 实现接口DisposableBean实现销毁清理工作");
    }
}
