package com.jjcc.feign.remote;

import org.springframework.stereotype.Component;

/**
 * fallback方法
 * @author Jjcc
 * @version 1.0.0
 * @className HelloRemoteImpl.java
 * @createTime 2020年01月14日 15:42:00
 */
@Component
public class HelloRemoteImpl implements IHelloRemote {
    @Override
    public String hello(String name) {
        return "Hello World！！！";
    }
}
