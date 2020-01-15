package com.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Jjcc
 * @version 1.0.0
 * @className HelloController.java
 * @createTime 2020年01月15日 10:22:00
 */
@RestController
public class HelloController {

    @Value("${info.profile:error}")
    private String hello;

    @GetMapping("hello")
    public Mono<String> hello() {
        return Mono.justOrEmpty(hello);
    }


}
