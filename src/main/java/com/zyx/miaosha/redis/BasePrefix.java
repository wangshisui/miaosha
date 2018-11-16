package com.zyx.miaosha.redis;

/**
 * @Author:zhangyx
 * @Date:Created in 21:232018/11/13
 * @Modified By:
 */
public class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix){ //默认0为永不过期
        this(0,prefix);
    }
    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds=expireSeconds;
        this.prefix=prefix;
    }
    @Override
    public int expireSeconds() { //默认0为永不过期

        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String classname=getClass().getSimpleName();
        return classname+":"+prefix;
    }
}
