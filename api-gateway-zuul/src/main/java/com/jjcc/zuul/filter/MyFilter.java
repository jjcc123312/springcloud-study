package com.jjcc.zuul.filter;

import com.fasterxml.jackson.core.filter.TokenFilter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 自定义Zuul的个过滤器。
 * 我们假设有这样一个场景，因为服务网关应对的是外部的所有请求，为了避免产生安全隐患，我们需要对请求做一定的限制，
 * 比如请求中含有Token便让请求继续往下走，如果请求不带Token就直接返回并给出提示。
 * @author Administrator
 * @version 1.0.0
 * @description
 * @className MyFilter.java
 * @createTime 2020年02月01日 21:15:00
 */
public class MyFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(MyFilter.class);


    /**
     * 过滤器的类型，它决定过滤器在请求的生命周期哪个环节执行。
     * 这里定义了 pre，代表在请求被路由之前执行。
     * @title filterType
     * @author Jjcc
     * @return java.lang.String
     * @createTime 2020/2/1 0001 21:18
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * filter执行顺序，通过数字指定。
     * 数字越大代表执行顺序越低
     * @title filterOrder
     * @author Jjcc
     * @return int
     * @createTime 2020/2/1 0001 21:21
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有请求都会生效。
     * 实际运用中我们可以利用该函数来指定过滤器的有效范围。
     * 可通过RequestContext.getCurrentContext()方法获取请求体。根据请求体中的内容进行判断。
     * @title shouldFilter
     * @author Jjcc
     * @return boolean
     * @createTime 2020/2/1 0001 21:22
     */
    @Override
    public boolean shouldFilter() {
        return false;
    }


    /**
     * 过滤器的具体逻辑
     * @title run
     * @author Jjcc
     * @return java.lang.Object
     * @createTime 2020/2/1 0001 21:27
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("--->> TokenFilterP{}，{}", request.getMethod(), request.getRequestURL().toString());

        // 获取请求的参数
        String token = request.getParameter("token");

        Optional<String> optional = Optional.ofNullable(token);

        if (optional.isPresent()) {
            // 不为空，对请求进行路由
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);
        } else {
            // 如果为空，不对其进行路由
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(400);
            ctx.setResponseBody("token is empty");
            ctx.set("ifSuccess", "false");
        }
        return null;
    }
}






