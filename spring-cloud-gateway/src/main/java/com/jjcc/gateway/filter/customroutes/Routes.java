package com.jjcc.gateway.filter.customroutes;

import com.jjcc.gateway.filter.customfilter.RateLimitByIPGatewayFilter;
import com.jjcc.gateway.filter.customfilter.RequestFilter;
import com.jjcc.gateway.filter.globalfilter.CustomGlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 将自定义路由以及自定义全局顾虑器加载进spring容器。
 *
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className FilterChain.java
 * @createTime 2020年02月12日 20:56:00
 */
@Configuration
public class Routes {

    /**
     * 自定义路由。采用函数式接口编程。
     * .filter(f -> f.filter(...))中创建的对象就是自定义过滤器。
     *
     * @param routeLocatorBuilder 参数
     * @return org.springframework.cloud.gateway.route.RouteLocator
     * @title customerRouteLocator
     * @author Jjcc
     * @createTime 2020/2/12 0012 21:09
     */
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r ->
                        r.path("/fluent/customer/**")
                                .filters(f -> f.stripPrefix(2)
                                        .filter(new RequestFilter()))
                                .uri("lb://EUREKA-CONSUMER")
                                .id("fluent_customer_service")

                ).build();

    }

    /**
     * 全局过滤器
     * @return org.springframework.cloud.gateway.filter.GlobalFilter
     * @title customGlobalFilter
     * @author Jjcc
     * @createTime 2020/2/12 0012 21:57
     */
    @Bean
    public GlobalFilter customGlobalFilter() {
        return new CustomGlobalFilter();
    }

    /**
     * 一个包含通过IP限流策略的路由。
     * @title rateLimitByIpGatewayFilter
     * @author Jjcc
     * @param routeLocatorBuilder
     * @return org.springframework.cloud.gateway.route.RouteLocator
     * @createTime 2020/2/12 0012 23:03
     */
    @Bean
    public RouteLocator rateLimitByIpGatewayFilter(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route(r ->
                r.path("/throttle/customer/**")
                        .filters(f -> f.stripPrefix(2).filter(new RateLimitByIPGatewayFilter(10, 1, Duration.ofSeconds(1))))
                .uri("lb://EUREKA-CONSUMER")
                .id("throttle_customer_service")
        ).build();
    }
}
