package com.jjcc.consummer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jjcc
 * @version 1.0.0
 * @className HelloController.java
 * @createTime 2020年01月08日 14:20:00
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    private RestTemplate restTemplate;

    @Autowired
    public HelloController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{name}")
    public String hello(@PathVariable String name) {
        String url = "http://eureka-produce/hello/" + name;
        return restTemplate.getForObject(url, String.class);
    }
}
