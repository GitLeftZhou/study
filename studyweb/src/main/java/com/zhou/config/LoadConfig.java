package com.zhou.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * servlet3.0+ 以上支持此种配置(Tomcat7+)
 * spring 配置引导类
 *      在此类中声明如何引入spring,类似在web.xml中引入spring.xml以及其他相关配置
 *
 * 在servlet3.0+中,规范要求服务器启动时扫描
 *    *.jar/META-INF/services/javax.servlet.ServletContainerInitializer 文件中配置的全类名
 *    并加载，此规范可使web.xml不必需
 *
 *    spring-web中javax.servlet.ServletContainerInitializer=
 *        org.springframework.web.SpringServletContainerInitializer
 *
 */
public class LoadConfig
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 根容器,指定spring的配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    /**
     * 应用容器,指定DispatcherServlet的配置类
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { AppConfig.class };
    }

    /**
     * 对应web.xml中DispatcherServlet的servlet mapping
     * @return
     */
    @Override
    protected String[] getServletMappings() {
//        System.out.println("LoadConfig.getServletMappings");
        return new String[]{"/"};
    }

}
