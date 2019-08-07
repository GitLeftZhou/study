package com.zhou.spring.configuration;

import com.zhou.spring.aop.LogAspects;
import com.zhou.spring.aop.MathCalculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * 1.导入aop依赖
 * 2.建业务类MathCalculator,使用AOP打印业务逻辑运行时日志(方法前，方法后，方法异常)
 * 3.定义日志切面类LogAspects,并使用Aspect声明
 *      通知方法：
 *          前置通知@Before:目标方法运行前
 *          后置通知@After:目标方法运行后
 *          返回通知@AfterReturning:目标方法正常返回后
 *          异常通知@AfterThrowing:目标出现异常后运行
 *          环绕通知@Around:动态代理，手动推进目标方法运行(joinPoint.process())
 * 4.给切面类标注何时运行(通知注解)
 * 5.将切面类和逻辑类都加入容器
 * 6.EnableAspectJAutoProxy 启用注解AOP模式
 *      在配置文件开启基于注解版的切面功能：<aop:aspectJ-autoProxy></aop:aspectJ-autoProxy>
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

    @Bean
    public MathCalculator mathCalculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(MainConfigOfAop.class);
        MathCalculator calculator = applicationContext.getBean("mathCalculator", MathCalculator.class);
//        int result = calculator.div(10, 2);
//        System.out.println(result);
        int result2 = calculator.divAndAdd(10, 0,100);
//        System.out.println(result2);
    }
}
