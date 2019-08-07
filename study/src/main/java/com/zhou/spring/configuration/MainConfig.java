package com.zhou.spring.configuration;

import com.zhou.spring.configuration.condition.MyImportBeanDefinitionRegistrar;
import com.zhou.spring.configuration.filter.MyImportSelector;
import com.zhou.spring.configuration.filter.MyTypeFilter;
import com.zhou.vo.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/**
 * Configuration 声明这是个配置类
 * ComponentScans 扫描多个，适用于JDK1.8以下，1.8+可以直接多次写ComponentScan，不需要ComponentScans包装
 *  ComponentScan 可以扫描传入参数包中使用了 Controller Service Repository Component的类
 *      excludeFilters = Filter[] 指定扫描的时候按照哪些规则排除组件
 *      includeFilters = Filter[] 指定扫描的时候只需要包含哪些组件 需要与useDefaultFilters=false搭配使用
 *      FilterType.ANNOTATION 按照注解过滤
 *      FilterType.ASSIGNABLE_TYPE 按照给定的类型
 *      FilterType.ASPECTJ ASPECTJ 表达式
 *      FilterType.REGEX 正则表达式
 *      FilterType.CUSTOM 自定义
 * 给容器中注册组件
 *     1).@ComponentScan + @Controller @Service @Repository @Component
 *     2).@Bean
 *     3).@Import 适用于快速注册无参bean,id默认是全类名
 *          ImportSelector 使用自定义导入选择器
 *          ImportBeanDefinitionRegistrar 手动注册
 *     4).使用spring提供的BeanFactory
 */
@Import({Person.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
@Configuration
@ComponentScans(value = {
        @ComponentScan(value = "com.zhou.spring",excludeFilters
                = {
                   //@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
                   //@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {PersonService.class})
                   @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class})
                  })
})
public class MainConfig {

    /**
     * bean id 使用两种方式 注解传入，方法名 默认单实例
     * Bean(value = "person",initMethod = "",destroyMethod = "") 声明初始化方法和销毁方法
     *      适用于他人创建的bean,在注册时声明
     * @see com.zhou.spring.services.PersonService 实现初始化和销毁的另外两种方式
     *      适用于自己创建的bean，在创建时就指定初始化和销毁，防止使用时遗漏
     *
     * @return
     */
    @Bean(value = "person",initMethod = "",destroyMethod = "")
    public Person person(){
        return new Person("zhangsan","18");
    }

    /**
     * 给容器中注册组件方法4示例
     * 此处代码是在容器中注册MySpringFactoryBean，
     *   applicationContext.getBean默认获得的是在MySpringFactoryBean中创建的类
     *   使用&前缀可以获得FactoryBean本身
     * @return
     */
    @Bean
    public MySpringFactoryBean mySpringFactoryBean(){
        return new MySpringFactoryBean();
    }


}
