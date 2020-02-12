package com.jjcc.gateway.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * gateway自定义过滤器
 * 现在假设我们要统计某个服务的响应时间
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className RequestFilter.java
 * @createTime 2020年02月07日 20:20:00
 */
public class RequestFilter implements GatewayFilter, Ordered {

    private static final Log LOG = LogFactory.getLog(GatewayFilter.class);
    private static final String ELAPSED_TIME_BEGIN = "requestTimeBegin";

    /**
     * 编写过滤器逻辑
     * @title filter
     * @author Jjcc
     * @param exchange
     * @param chain
     * @return reactor.core.publisher.Mono<java.lang.Void>
     * @createTime 2020/2/8 0008 12:28
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // chain.filter(exchange)方法之前属于“pre”过滤器。（在请求被路由之前执行的过滤器）
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    // Runnable中定义的就是“post”过滤器。（在请求被路由后执行的过滤器）
                    Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                    Optional<Long> startTime1 = Optional.ofNullable(startTime);
                    if (startTime1.isPresent()) {
                        System.out.println("开始时间：" + startTime);
                        System.out.println("结束时间：" + System.currentTimeMillis());
                        LOG.info(exchange.getRequest().getURI().getRawPath() +
                                "：" + (System.currentTimeMillis() - startTime1.orElseThrow(
                                RuntimeException::new
                        )));
                    }
                })
        );
    }

    /**
     * 过滤器设置优先级别。值越大，优先级越低。
     * @title getOrder
     * @author Jjcc
     * @return int
     * @createTime 2020/2/8 0008 12:28
     */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
