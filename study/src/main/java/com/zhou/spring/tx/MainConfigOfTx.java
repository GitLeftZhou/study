package com.zhou.spring.tx;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 1.开启事务管理器
 *      @see EnableTransactionManagement
 * 2.注册
 *      @see PlatformTransactionManager
 * 3.在方法上加上事务声明
 *      @see Transactional
 */
@EnableTransactionManagement
@Configuration
public class MainConfigOfTx {

    @Bean
    public DataSource dataSource(){
        Map properties = new HashMap();
        properties.put(DruidDataSourceFactory.PROP_URL,"");
        properties.put(DruidDataSourceFactory.PROP_USERNAME,"");
        properties.put(DruidDataSourceFactory.PROP_PASSWORD,"");
        properties.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME,"");

        DataSource dataSource = null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager transactionFactory(){
        return new DataSourceTransactionManager(dataSource());
    }
}
