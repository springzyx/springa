package com.example.demo.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/10/24 11:00
 * @Description:
 */
@Slf4j
@Configuration
public class FreemakerConfiguration {
    private static final String SECURITY_TLD = "/META-INF/security.tld";
    @Autowired
    freemarker.template.Configuration fconfiguration;
    @Autowired
    FreeMarkerViewResolver resolver;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    InternalResourceViewResolver internalResourceViewResolver;
    @Value("${jsRoot}")
    String jsRoot;
    @PostConstruct
    public void setConfigure() throws Exception{

        //...设置一些常用的全局变量
        fconfiguration.setSharedVariable("jsRoot",jsRoot);
        log.info("jsRoot:"+jsRoot);
        resolver.setOrder(0);
        internalResourceViewResolver.setContentType("text/html;charset=utf-8");
        log.info("postConstruct freemarker");
        freeMarkerConfigurer.getTaglibFactory().setClasspathTlds(Arrays.asList(SECURITY_TLD));
    }
}
