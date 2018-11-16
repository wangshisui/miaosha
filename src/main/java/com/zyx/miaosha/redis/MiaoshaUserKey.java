package com.zyx.miaosha.redis;

/**
 * @Author:zhangyx
 * @Date:Created in 22:222018/11/15
 * @Modified By:
 */
public class MiaoshaUserKey extends BasePrefix {

    public static final int TIKEN_EXISTS=3600*24*2;
    public MiaoshaUserKey(String prefix) {
        super(prefix);
    }

    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token=new MiaoshaUserKey(TIKEN_EXISTS,"tk");

}
