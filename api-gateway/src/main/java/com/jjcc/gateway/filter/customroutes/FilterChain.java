package com.jjcc.gateway.filter.customroutes;

import com.jjcc.gateway.filter.RequestFilter;
import com.jjcc.gateway.filter.filterfactory.ElapsedGatewayFilterFactory;
import com.jjcc.gateway.filter.globalfilter.GlobalFilterOne;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤链。
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className FilterChain.java
 * @createTime 2020年02月07日 20:49:00
 */
@Configuration
public class FilterChain {

    /**
     * 该链式编程中所有方法名都与配置文件中配置路由的属性名一样。
     * 配置predicate则不一样。
     * @title customerRouteLocator
     * @author Jjcc
     * @param builder
     * @return org.springframework.cloud.gateway.route.RouteLocator
     * @createTime 2020/2/8 0008 12:35
     */
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(r -> r.path("/fluent/customer/**")
                        .filters(f -> f.stripPrefix(2)
                                .filter(new RequestFilter())
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("lb://EUREKA-CONSUMER")
                        .order(0)
                        .id("fluent_customer_service")
                )
                .build();
    }

    /**
     * 将自定义全局过滤器注册进spring容器
     * @title myGlobalFilter
     * @author Jjcc
     * @return com.jjcc.gateway.filter.globalfilter.GlobalFilterOne
     * @createTime 2020/2/8 0008 14:02
     */
    @Bean
    public GlobalFilterOne myGlobalFilter() {
        return new GlobalFilterOne();
    }

    /**
     * 将自定义的过滤器工厂注册进spring容器
     * @title elapsedGatewayFilterFactory
     * @author Jjcc
     * @return com.jjcc.gateway.filter.factory.ElapsedGatewayFilterFactory
     * @createTime 2020/2/9 0009 15:39
     */
    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new ElapsedGatewayFilterFactory();
    }
}
