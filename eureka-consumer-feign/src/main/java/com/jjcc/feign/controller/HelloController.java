package com.jjcc.feign.controller;

import com.jjcc.feign.remote.IHelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jjcc
 * @version 1.0.0
 * @className HelloController.java
 * @createTime 2020年01月09日 23:01:00
 */
@RestController
@RequestMapping("/hello")
@RefreshScope
public class HelloController {

    private IHelloRemote helloRemote;

    @Value("${info.profile:error}")
    private String hello;

    @Autowired
    public HelloController(IHelloRemote iHelloRemote) {
        this.helloRemote = iHelloRemote;
    }

    @GetMapping("/{name}")
    public String hello1(@PathVariable String name) {
        return helloRemote.hello(name);
    }

    @GetMapping("info")
    public String hello() {
        return hello;
    }

}
