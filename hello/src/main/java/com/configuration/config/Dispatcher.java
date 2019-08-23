package com.configuration.config;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dispatcher extends DispatcherServlet {

    public Dispatcher(){
        System.out.println("Dispatcher.Dispatcher 构造器");
    }

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Dispatcher.doService begin");
        super.doService(request, response);
        System.out.println("Dispatcher.doService after");
    }
}
