package com.zyx.miaosha.controller;

import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.redis.MiaoshaUserKey;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.service.MiaoshaUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author:zhangyx
 * @Date:Created in 22:332018/11/15
 * @Modified By:
 */
@Controller
@RequestMapping("/goods")
public class GoodController {
    @Autowired
    private MiaoshaUserService userService;
//商品列表
@RequestMapping("/to_list")
public String toLogin(Model model,MiaoshaUser user ){

    model.addAttribute("user",user);
    return "goods_list";
}

/*
@RequestMapping("/to_list")
    public String toLogin(Model model, @CookieValue(value =MiaoshaUserService.COOKIE_NAME_TOKEN,required = false)
        String cookieToken, @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false)String paramtoken,
                          HttpServletResponse response){
if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramtoken)){
    return "login";
}
String token=StringUtils.isEmpty(paramtoken)?cookieToken:paramtoken;

MiaoshaUser user=userService.getByToken(response,token);
  model.addAttribute("user",user);
    return "goods_list";
}
*/

/*//商品详情页
@RequestMapping("/to_detail")
public String detail(Model model, @CookieValue(value =MiaoshaUserService.COOKIE_NAME_TOKEN,required = false)
        String cookieToken, @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN,required = false)String paramtoken,
                      HttpServletResponse response){
    if(StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramtoken)){
        return "login";
    }
    String token=StringUtils.isEmpty(paramtoken)?cookieToken:paramtoken;

    MiaoshaUser user=userService.getByToken(response,token);
    model.addAttribute("user",user);
    return "goods_list";
}*/
}
