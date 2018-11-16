package com.zyx.miaosha.utils;

import java.util.UUID;

/**
 * @Author:zhangyx
 * @Date:Created in 22:192018/11/15
 * @Modified By:
 */
public class UUIDUtil {

    public static String UUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
