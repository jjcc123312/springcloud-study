package com.jjcc.gateway.filter.globalfilter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * 自定义GlobalFilter全局顾虑器。
 * 这个过滤器相当于是 “pre”过滤器。
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className GlobalFilter.java
 * @createTime 2020年02月12日 21:42:00
 */
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        Optional<String> tokenOpt = Optional.ofNullable(token);

        if (!tokenOpt.isPresent()) {
            // 鉴权不通过
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 鉴权通过
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 8;
    }
}
