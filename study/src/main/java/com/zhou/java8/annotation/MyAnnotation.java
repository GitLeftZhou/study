package com.zhou.java8.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

@Repeatable(value = MyAnnotations.class)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    /**
     * 注解语法
     *     @Target 声明注解的使用的地方
     *     @Retention 声明注解的生命周期
     *
     *     @interface 声明为注解类
     *     参数类型 参数名字  default 默认值;
     *
     * 只有一个参数建议默认为 value
     * 注解仅仅是一个定义，若需要发挥作用需要另一个类使用反射获取注解信息做相应的操作
     *     反射获取注解：获取class,获取class的注解
     *                   获取method，获取method的注解
     *                   获取Field，获取Field的注解
     */
    String value() default "";
    //int age() default -1;
    //String [] args();
}
