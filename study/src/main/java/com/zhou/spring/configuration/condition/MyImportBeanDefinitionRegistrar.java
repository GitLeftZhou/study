package com.zhou.spring.configuration.condition;

import com.zhou.vo.Person;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry 类注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        //可以实现自有逻辑
        // 定义bean的类型，以及scope
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Person.class);
        registry.registerBeanDefinition("person03",beanDefinition);
    }
}
