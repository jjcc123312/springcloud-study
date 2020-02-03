package com.jjcc.zuul;

import com.jjcc.zuul.filter.MyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @EnableZuulProxy 开启对zuul的支持
 * @author Administrator
 */
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayZuulApplication.class, args);
    }

    @Bean
    public MyFilter tokenFilter() {
        return new MyFilter();
    }

}
