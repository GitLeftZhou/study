package com.zhou.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

/**
 * !!!! GetMapping 必须在EnableWebMvc后才能使用
 * 按照spring建议规范，应将spring springmvc分为两个容器
 *      此类为springmvc容器
 */
@Configuration
@ComponentScan(basePackages = {"com.zhou"},
        includeFilters ={@ComponentScan.Filter(classes = {Controller.class})},
        useDefaultFilters = false)
//开启MVC定制功能
@EnableWebMvc
@EnableAsync
public class AppConfig extends WebMvcConfigurerAdapter {

    public AppConfig(){
        System.out.println("AppConfig.AppConfig 初始化构建成功");
    }

    /**
     * 视图解析器
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //jsp("/WEB-INF/", ".jsp");
        //registry.jsp();
        registry.jsp("/WEB-INF/views", ".jsp");
    }

    /**
     * 静态资源访问
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }



    /**
     * 注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);//默认空实现
        //注册自定义拦截器
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }

    /**
     * 自定义类型转换器 常用语时间，数字转换
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);

    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/public", "classpath:/static/")
//                .setCachePeriod(31556926);
//    }
}
