package com.zhou.java8.annotation;

//import com.sun.istack.internal.NotNull;
import org.junit.Test;

import javax.annotation.Nonnull;
//import javax.validation.constraints.NotNull;
//import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 可重复注解与类型注解
 *    重复注解需要一个容器
 *
 */
public class AnnotationDemo {

    //这个就是类型注解 对数据类型进行注解  声明这个类型不能为空
    private @Nonnull Object obj ;

    @MyAnnotation(value = "Hello")
    @MyAnnotation(value = "World")
    public void show(@MyAnnotation(value = "这是一个类型注解") String str){

    }

    @Test
    public void test1(){
        /**
         * 获取注解信息
         */
        Class<AnnotationDemo> clazz = AnnotationDemo.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            MyAnnotation[] annotations = method.getAnnotationsByType(MyAnnotation.class);
            for (MyAnnotation annotation : annotations) {
                System.out.println(annotation.value());
            }
        }
    }
}
