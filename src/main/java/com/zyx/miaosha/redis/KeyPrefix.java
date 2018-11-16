package com.zyx.miaosha.redis;

/**
 * @Author:zhangyx
 * @Date:Created in 21:222018/11/13
 * @Modified By:
 */
public interface KeyPrefix {

    public int expireSeconds();
    public String getPrefix();
}
