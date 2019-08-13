package com.zhou.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import java.util.Arrays;

/**
 * 在spring配置文件开启基于注解版的AOP功能：
 *      <aop:aspectJ-autoProxy></aop:aspectJ-autoProxy>
 * 依赖建议最低版本：
 *      spring-aspects-4.3.25.jar aspectjweaver-1.8.9.jar
 *
 * Aspect声明这是一个Aspect切面类
 */
@Aspect
public class LogAspects {

    /**
     * 公共的切入点表达式
     *
     * 常见切入点表达式的例子：
     *      任意公共方法的执行：
     *          execution(public * *(..))
     *      任何一个以“set”开始的方法的执行：
     *          execution(* set*(..))
     *      AccountService 接口的任意方法的执行：
     *          execution(* com.xyz.service.AccountService.*(..))
     *      定义在service包里的任意方法的执行：
     *          execution(* com.xyz.service.*.*(..))
     *      定义在service包或者子包里的任意类的任意方法的执行：
     *          execution(* com.xyz.service..*.*(..))
     */
    @Pointcut(value = Constants.POINT_CUT)
    public void pointCut(){}

    //本类中可以直接使用方法名
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) throws Exception {

        //参数为自定义类型时，需实现toString()，打印内部属性值
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        final String name = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println("LogAspects.logStart"+"--->["
                + name +"]开始运行,参数列表是:{"+ Arrays.asList(args)+"}");
//        if ((int)args[1] == 0){
//            throw new Exception("被除数不能为0");
//        }
    }

    /**
     * 正常异常结束都会执行
     * 其他类使用需要使用pointCut的全路径名
     */
    @After("com.zhou.spring.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        final String name = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println("LogAspects.logEnd"+"--->["
                +name+"]执行结束");
    }

    /**
     *
     * @param joinPoint  这个参数必须放在参数列表的第一个，不然spring无法识别
     * @param result 用于接收返回值
     */
    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        final String name = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println("LogAspects.logReturn"
                +"--->["+name+"]正常返回,参数列表是:{"+ Arrays.asList(args)+"},返回值为:{"+result+"}");
    }


    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(JoinPoint joinPoint,Exception exception){
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        final String name = signature.getDeclaringTypeName() + "." + signature.getName();
        System.out.println("LogAspects.logException"
                +"--->["+name+"]异常,参数列表是:{"+ Arrays.asList(args)+"},异常信息为:{"+exception+"}");
    }
}
