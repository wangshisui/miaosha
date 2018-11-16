package com.zyx.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.awt.*;

/**
 * @Author:zhangyx
 * @Date:Created in 22:062018/11/13
 * @Modified By:
 */
public class MD5Util {
    public static String md5(String src){
        return DigestUtils.md2Hex(src);
    }
    private static final String salt="1a2b3c4d";
    public static String inputPassToFromPass(String inputPass){
String str=""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
return md5(str);
    }

    public static String formPassToDbPass(String input,String salt){
        String frompass=inputPassToFromPass(input);
        String dbpass=""+salt.charAt(0)+salt.charAt(2)+frompass+salt.charAt(5)+salt.charAt(4);
        return dbpass;
    }

    public static void main(String[] args) {
        String s=MD5Util.formPassToDbPass("123456","1a2b3c4d");
        System.out.println(s);
    }
}
