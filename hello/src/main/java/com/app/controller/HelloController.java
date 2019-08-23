package com.app.controller;

import com.app.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/hello")
public class HelloController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/hello",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hello(){
        return helloService.hello("张三");
    }
}
