package com.zyx.miaosha.controller;

import com.zyx.miaosha.Result.Result;
import com.zyx.miaosha.domain.User;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.redis.UserKey;
import com.zyx.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;

/**
 * @Author:zhangyx
 * @Date:Created in 19:232018/11/11
 * @Modified By:
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;
    @RequestMapping("/")
    public String thymeleaf(Model model){
     model.addAttribute("name","张玉鑫");
     return "hello";

    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result dbGet() {
        User user = userService.getById(1);

        return Result.success(user);

    }
    @RequestMapping("/db/redis")
    @ResponseBody
    public Result<User> getredis() {
       User user=redisService.get(UserKey.getById,""+1,User.class);

        return Result.success(user);

    }

    @RequestMapping("/set/redis")
    @ResponseBody
    public Result<String> setredis() {
        boolean set=redisService.set(UserKey.getById,""+2,"你好世界");
        String v2=redisService.get(UserKey.getById,""+2,String.class);

        return Result.success(v2);

    }

}
