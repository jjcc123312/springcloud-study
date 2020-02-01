package com.jjcc.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Jjcc
 * @version 1.0.0
 * @className HelloController.java
 * @createTime 2020年01月08日 11:36:00
 */
@RestController
@RequestMapping(value = "/hello")
@RefreshScope
public class HelloController {

    @Value("${info.profile:error}")
    private String port;

    @GetMapping("{name}")
    public String hello(@PathVariable String name) {
        return "Hello，" + name + "。当前时间：" + LocalDateTime.now() + "端口号：" + port;
    }


}
