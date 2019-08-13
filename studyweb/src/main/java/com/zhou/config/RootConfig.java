package com.zhou.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 *  按照spring建议规范，应将spring springmvc分为两个容器
 *     此类为spring容器
 */
@Configuration
@ComponentScan(basePackages = {"com.zhou"},
        excludeFilters ={@ComponentScan.Filter(classes={Controller.class})})
//@EnableTransactionManagement
//@EnableCaching
@PropertySource("classpath:db.properties")
public class RootConfig {


    @Autowired
    private Environment environment;

//    @Bean("dataSource")
    public DataSource dataSource(){

        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("jdbc/pcis");
        jndiObjectFactoryBean.setResourceRef(true);
        jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);

        DataSource dataSource = (DataSource) jndiObjectFactoryBean.getObject();

        return dataSource;
    }

   /* *//**
     * 整合第三方bean
     * @return
     *//*
    //
    @Bean(value="druidDataSource")
    @Lazy(false)
    public DataSource druidDataSource() {
        DataSource dataSource = null;
        try {
            Map properties = new HashMap();
            properties.put(DruidDataSourceFactory.PROP_URL,
                    environment.getProperty("jdbcUrl"));

            properties.put(DruidDataSourceFactory.PROP_USERNAME,
                    environment.getProperty("jdbcUsername"));

            properties.put(DruidDataSourceFactory.PROP_PASSWORD,
                    environment.getProperty("jdbcPassword"));

            properties.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME,
                    environment.getProperty("jdbcDriver"));

            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
        }
        return dataSource;

    }*/

    /**
     * 指定事务管理器
     * @return
     */
//    @Bean
    public PlatformTransactionManager transactionFactory(){
        return new DataSourceTransactionManager(dataSource());
    }

}
