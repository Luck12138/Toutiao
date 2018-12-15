package com.amaker.toutiao.configuration;


import com.amaker.toutiao.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: toutiao
 * @Date: 2018/12/15 0015 19:39
 * @Author: GHH
 * @Description:
 */
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(passportInterceptor);
        super.addInterceptors(registry);
    }
}
