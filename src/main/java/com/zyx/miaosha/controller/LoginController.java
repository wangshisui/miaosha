package com.zyx.miaosha.controller;

import com.zyx.miaosha.Result.CodeMsg;
import com.zyx.miaosha.Result.Result;
import com.zyx.miaosha.execption.GlobelException;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.service.MiaoshaUserService;
import com.zyx.miaosha.vo.LoginVo;
import com.zyx.miaosha.vo.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author:zhangyx
 * @Date:Created in 22:182018/11/13
 * @Modified By:
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    private RedisService redisService;

    //日志
    private static Logger log= LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, LoginVo loginVo){
        //log.info(loginVo.toString());
        System.out.println(loginVo.toString());
        //参数校验
        String passinput=loginVo.getPassword();
        String mobile=loginVo.getMobile();
        if(StringUtils.isEmpty(passinput)){
            //return Result.error(CodeMsg.PASSWORD_EMPTY);
            throw new GlobelException(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            //return Result.error(CodeMsg.MOBILE_EMPTY);
            throw new GlobelException(CodeMsg.MOBILE_EMPTY);
        }

        if(!ValidatorUtil.isMobile(mobile)){
           // return Result.error(CodeMsg.MOBILE_ERROR);
            throw new GlobelException(CodeMsg.MOBILE_ERROR);
        }
       miaoshaUserService.login(response,loginVo);
        return Result.success(true);
   /* public Result<Boolean> doLogin(LoginVo loginVo){
       //log.info(loginVo.toString());
        System.out.println(loginVo.toString());
        //参数校验
        String passinput=loginVo.getPassword();
        String mobile=loginVo.getMobile();
        if(StringUtils.isEmpty(passinput)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }

        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
       CodeMsg msg= miaoshaUserService.login(loginVo);
        if (msg.getCode()==0){
            return Result.success(true);
        }else {
            return Result.error(msg);
        }*/

    }
}
