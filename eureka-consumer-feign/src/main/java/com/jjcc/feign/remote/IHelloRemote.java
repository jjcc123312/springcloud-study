package com.jjcc.feign.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jjcc
 * @version 1.0.0
 * @className IHelloRemote.java
 * @createTime 2020年01月09日 22:58:00
 */
@FeignClient(name = "eureka-produce")
public interface IHelloRemote {

    /**
     * 远程服务调用test
     * @title hello
     * @author Jjcc
     * @param name 参数
     * @return java.lang.String
     * @createTime 2020/1/9 23:01
     */
    @GetMapping("hello/{name}")
    String hello(@PathVariable String name);
}
