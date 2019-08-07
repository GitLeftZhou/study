package com.zhou.spring.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 在spring组件类中获取spring容器的方法：实现ApplicationContextAware接口
 * 使用其他的spring底层组件都可以参照XXXAware
 * @see org.springframework.beans.factory.Aware Ctrl+H 查看子接口
 *
 */
@Service
public class AwareService implements ApplicationContextAware{

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
}
