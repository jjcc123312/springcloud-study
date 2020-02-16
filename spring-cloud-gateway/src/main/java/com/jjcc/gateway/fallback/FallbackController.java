package com.jjcc.gateway.fallback;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className FallbackController.java
 * @createTime 2020年02月15日 20:40:00
 */
@RestController
@Log4j2
public class FallbackController {

    @GetMapping("/fallback")
    public String fallback() {
        log.info("进入了fallback!!!");
        return "Hell World/no from gateway！！";
    }
}
