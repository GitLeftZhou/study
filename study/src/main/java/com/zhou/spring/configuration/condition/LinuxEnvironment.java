package com.zhou.spring.configuration.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxEnvironment implements Condition {
    /**
     *
     * @param context 判断条件能使用的上下文
     * @param metadata 注解信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //ioc使用的beanFactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //
        Environment environment = context.getEnvironment();
        if (environment.getProperty("os.name").toLowerCase().contains("linux")){
            return true;
        }

        BeanDefinitionRegistry registry = context.getRegistry();
        boolean isHavePerson02 = registry.containsBeanDefinition("person02");
        System.out.println("isHavePerson02:"+isHavePerson02);
        //
        ResourceLoader resourceLoader = context.getResourceLoader();
        return false;
    }
}
