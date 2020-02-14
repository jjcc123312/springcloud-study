package com.jjcc.gateway.filter.customfilter;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * gateway自定义过滤器
 * 现在假设我们要统计某个服务的响应时间。
 * 自定义过滤器后还需要放到自定义路由中。
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className RequestFilter.java
 * @createTime 2020年02月12日 20:42:00
 */
@Log4j2
public class RequestFilter implements GatewayFilter, Ordered {

    private static final String ELAPSED_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // "pre"过滤器逻辑定义区。
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());

        // chain.filter(exchange).then后面是“post”过滤器逻辑定义区。
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
            Optional<Long> startTimeOpt = Optional.ofNullable(startTime);
            startTimeOpt.ifPresent(aLong -> log.info(exchange.getRequest().getURI().getRawPath() + "路径响应时间：" +
                    (System.currentTimeMillis() - aLong)));
        }));
    }

    /**
     * 执行顺序，值越大，执行顺序越低。
     * @title getOrder
     * @author Jjcc
     * @return int
     * @createTime 2020/2/12 0012 20:54
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
