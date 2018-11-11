package com.zyx.miaosha.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author:zhangyx
 * @Date:Created in 21:342018/11/11
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisConfig {
    private String host;

    private int port;

    private int timeout; //秒

    private String password;

    private int poolMaxTotal;

    private int poolMaxIdle;

    private int poolMaxWait;//秒
}
