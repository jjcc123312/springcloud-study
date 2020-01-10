package com.jjcc.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @EnableFeign 这个注解是通知Springboot在启动的时候扫描被@FeignClient修饰的类。
 * @FeignClient 这个注解在进行远程调用的时候会用到。
 * @author Jjcc
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerFeignApplication.class, args);
    }

}
