package com.jjcc.gateway.filter.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * 自定义单个参数的过滤器工厂
 * 需要继承 AbstractGatewayFilterFactory类。
 * 注意：工厂类中定义的参数名 "withParam"，即key名必须与内部类中的参数名（用于接收配置文件中参数值得变量）一致。
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className ElapsedGatewayFilterFactory.java
 * @createTime 2020年02月08日 16:40:00
 */
public class ElapsedGatewayFilterFactory
        extends AbstractGatewayFilterFactory<ElapsedGatewayFilterFactory.Config> {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);
    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";
    private static final String KEY = "withParam";

    /**
     * 自定义过滤器工厂的构造方法，必须调用父类的构造器把Config类型传递过去
     * 否则会报 ClassCastException异常。
     * @title ElapsedGatewayFilterFactory
     * @author Jjcc
     * @createTime 2020/2/9 0009 16:47
     */
    public ElapsedGatewayFilterFactory() {
        super(Config.class);
    }

    /**
     * 重写父类方法，将定义的key存储进List。
     * @title shortcutFieldOrder
     * @author Jjcc
     * @return java.util.List<java.lang.String>
     * @createTime 2020/2/9 0009 16:49
     */
    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());
            return chain.filter(exchange).then(
                    Mono.fromRunnable(() -> {
                                Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                                Optional<Long> startTimeOpt = Optional.ofNullable(startTime);
                                if (startTimeOpt.isPresent()) {
                                    StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getPath())
                                            .append("所用时间:")
                                            .append(System.currentTimeMillis() - startTimeOpt.orElseThrow(RuntimeException::new))
                                            .append("ms");
                                    if (config.isWithParam()) {
                                        sb.append("；parameter：").append(exchange.getRequest().getQueryParams());
                                    }
                                    log.info(sb.toString());
                                }
                            }
                    )
            );
        };
    }

    public static class Config {
        // 接收配置文件参数的变量
        private boolean withParam;

        public boolean isWithParam() {
            return withParam;
        }

        public void setWithParam(boolean setWithParam) {
            this.withParam = setWithParam;
        }

    }

}
