package com.jjcc.gateway.filter.ratelimiter;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 通过 RequestRateLimiter 实现限流策略。
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className Config.java
 * @createTime 2020年02月14日 14:11:00
 */
@Configuration
public class Config {

    /**
     * 根据IP限流
     * @title ipKeyResolver
     * @author Jjcc
     * @return org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @createTime 2020/2/14 0014 20:41
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange ->
            Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName());
    }

    /**
     * 根据User参数限流
     * @title userKeyResolver
     * @author Jjcc
     * @return org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @createTime 2020/2/14 0014 20:47
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getQueryParams().getFirst("user")));
    }

    /**
     * 根据hostName限流
     * @title hostNameResolver
     * @author Jjcc
     * @return org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @createTime 2020/2/14 0014 20:51
     */
    @Bean
    public KeyResolver hostNameResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }

    /**
     * 根据uri限流
     * @title uriKeyResolver
     * @author Jjcc
     * @return org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
     * @createTime 2020/2/14 0014 20:53
     */
    @Primary
    @Bean(name="uriKeyResolver")
    public KeyResolver uriKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getURI()).getPath());
    }

}


