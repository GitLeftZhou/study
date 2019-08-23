package com.configuration.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * !!!! GetMapping 必须在EnableWebMvc后才能使用
 * 按照spring建议规范，应将spring springmvc分为两个容器
 *      此类为springmvc容器
 */
@Configuration
@ComponentScan(basePackages = {"com.app"},
        includeFilters ={@ComponentScan.Filter(value = {Controller.class})},
        useDefaultFilters = false)
//开启MVC定制功能
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {


    public AppConfig(){
        System.out.println("AppConfig.AppConfig 初始化构建成功");
    }

//    @Bean
//    public RequestMappingHandlerMapping requestMappingHandlerMapping(){
//        return new RequestMappingHandlerMapping();
//    }
//
//    @Bean
//    public RequestMappingHandlerAdapter requestMappingHandlerAdapter(){
//        return new RequestMappingHandlerAdapter();
//    }

   /* public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INFO/jsp");
        resolver.setSuffix(".jsp");
        return resolver;
    }
*/
    /**
     * 视图解析器 spring mvc 4+ 才支持
     * @param registry
     */
    /*
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //jsp("/WEB-INF/", ".jsp");
        //registry.jsp();
        registry.jsp("/WEB-INF/views", ".jsp");
    }
    */

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
