package com.zhou.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ConcertHandler2 implements InvocationHandler {
    private Concert star;
    private Concert singStar;

    public ConcertHandler2(Concert star,Concert singStar) {
        super();
        this.star = star;
        this.singStar = singStar;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        //AOP  在此处操作需拦截方法
        if(method.getName().equals("sing")){
            // 在此处做拦截前预处理
            System.out.println("方法调用前");
//            obj = method.invoke(star,args);
            // 我们来假唱
            singStar.sing();
            //在此处做拦截后处理
            System.out.println("方法调用后");
        } else if(method.getName().equals("signContract")){
            System.out.println("实现自己的方法，但是不太能细微的调整原方法");
        } else {
            //不做处理，直接调用
            obj = method.invoke(star,args);
        }
        return obj;
    }
}
