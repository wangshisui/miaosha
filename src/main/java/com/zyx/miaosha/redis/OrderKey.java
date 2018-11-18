package com.zyx.miaosha.redis;

/**
 * @Author:zhangyx
 * @Date:Created in 17:312018/11/18
 * @Modified By:
 */
public class OrderKey extends BasePrefix{
    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid=new OrderKey("moug");
}
