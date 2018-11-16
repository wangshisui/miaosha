package com.zyx.miaosha.vo;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 进行手机号的规则校验
 * @Author:zhangyx
 * @Date:Created in 21:072018/11/14
 * @Modified By:
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern=Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if (StringUtils.isEmpty(src)){
            return false;
        }
        Matcher matcher=mobile_pattern.matcher(src);
        return matcher.matches();
    }
}
