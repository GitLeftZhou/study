package com.configuration.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

/**
 *  按照spring建议规范，应将spring springmvc分为两个容器
 *     此类为spring容器
 */
@Configuration
@ComponentScan(basePackages = {"com.app"},
        excludeFilters ={@ComponentScan.Filter(value={Controller.class})})
public class RootConfig {




}
