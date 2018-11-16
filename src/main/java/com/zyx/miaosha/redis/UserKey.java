package com.zyx.miaosha.redis;

/**
 * @Author:zhangyx
 * @Date:Created in 21:282018/11/13
 * @Modified By:
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix){
        super(prefix);
    }
    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey getById=new UserKey("id");
    public static UserKey getByName=new UserKey("name");
}
