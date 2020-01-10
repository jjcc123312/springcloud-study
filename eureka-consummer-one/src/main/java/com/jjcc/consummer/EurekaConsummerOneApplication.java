package com.jjcc.consummer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jjcc
 */
@SpringBootApplication
public class EurekaConsummerOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaConsummerOneApplication.class, args);
    }

    /**
     * @LoadBalanced 注解表明这个RestTemplate开启负载均衡的功能。
     * @title restTemplate
     * @author Jjcc
     * @return org.springframework.web.client.RestTemplate
     * @createTime 2020/1/8 14:17
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
