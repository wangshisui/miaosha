package com.zyx.miaosha.config;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author:zhangyx
 * @Date:Created in 22:172018/11/16
 * @Modified By:
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    @Autowired
    UserArguementResover userArguementResover;
     @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
argumentResolvers.add(userArguementResover);

    }

}
