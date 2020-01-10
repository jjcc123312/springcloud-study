package com.jjcc.feign.controller;

import com.jjcc.feign.remote.IHelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HelloController {

    private IHelloRemote helloRemote;

    @Autowired
    public HelloController(IHelloRemote iHelloRemote) {
        this.helloRemote = iHelloRemote;
    }

    @GetMapping("/{name}")
    public String hello(@PathVariable String name) {
        return helloRemote.hello(name);
    }
}
