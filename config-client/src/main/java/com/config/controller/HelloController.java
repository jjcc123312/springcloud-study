package com.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @RefreshScope使用该类的注解，会在接到SpringCloud配置中心的配置更新时，自动将新的配置更新到该类对应的字段中
 * @author Jjcc
 * @version 1.0.0
 * @className HelloController.java
 * @createTime 2020年01月15日 10:22:00
 */
@RestController
@RefreshScope
public class HelloController {

    @Value("${info.profile:error}")
    private String hello;

    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.justOrEmpty(hello);
    }


}
