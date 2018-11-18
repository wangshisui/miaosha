package com.zyx.miaosha.controller;

import com.zyx.miaosha.Result.Result;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:zhangyx
 * @Date:Created in 16:122018/11/17
 * @Modified By:
 */
@Controller
@RequestMapping("/user")
public class UserTestController {
@Autowired
    MiaoshaUserService miaoshaUserService;
@Autowired
    RedisService redisService;

@RequestMapping("/info")
@ResponseBody
    public Result<MiaoshaUser> getUserTest(Model model,MiaoshaUser miaoshaUser){

    return Result.success(miaoshaUser);
}

}
