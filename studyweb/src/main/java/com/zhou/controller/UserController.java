package com.zhou.controller;

import com.zhou.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;

@Controller
//@RequestMapping("/user") 模块
public class UserController {

    public UserController(){
        System.out.println("UserController.UserController 构建成功");
    }

    @Autowired
    UserService userService;

    /**
     *  * !!!! GetMapping 必须在EnableWebMvc后才能使用
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String user(){
        System.out.println("UserController.user");
        return userService.getUser()+"just for fun";
    }



    @GetMapping("/asnyc01")
    @ResponseBody
    public Callable<String> asnyc01(){
         /* 适用于一般异步模型*/


        System.out.println("UserController.asnyc01");

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("UserController.call");
                return "UserController.asnyc01";
            }
        };

    }


    @ResponseBody
    @GetMapping("/asnyc02")
    public DeferredResult<Object> asnyc02(){
        DeferredResult deferredResult =
                new DeferredResult(30000l,"error message");
         /* 适用于生产者消费者模型
         *
         * 生产者
         * 将deferredResult 存入队列
         *
         * 消费者
         * 从队列中拿出deferredResult 并将值 deferredResult.setResult(Object);
         */


        return deferredResult;
    }


    /**
     * servlet3.0+ 原生异步处理
     * 1.新建servlet
     * 2.声明注解 @WebServlet(value = "async",asyncSupported = true)
     * 3.在doXXX方法中如下例方法实现
     */

    public void originalAsnyc(HttpServletRequest request, HttpServletResponse response){
        AsyncContext asyncContext = request.startAsync();
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    //do something
                    asyncContext.complete();
                    ServletResponse resp = asyncContext.getResponse();
                    resp.getWriter().write("hello sync");
                } catch (IOException e) {
                }

            }
        });
    }


}
