package com.zyx.miaosha.service;

import com.zyx.miaosha.Result.CodeMsg;
import com.zyx.miaosha.dao.MiaoshaUserDao;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.execption.GlobelException;
import com.zyx.miaosha.redis.MiaoshaUserKey;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.utils.MD5Util;
import com.zyx.miaosha.utils.UUIDUtil;
import com.zyx.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:zhangyx
 * @Date:Created in 19:482018/11/14
 * @Modified By:
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN="token";
        @Autowired
        private MiaoshaUserDao miaoshaUserDao;

        @Autowired
        private RedisService redisService;


            public MiaoshaUser getById(Long id){
            return  miaoshaUserDao.getById(id);
            }

    public Boolean login(HttpServletResponse response, LoginVo loginVo){
        if(loginVo==null){
            // return CodeMsg.SERVER_ERROR;
            throw new GlobelException(CodeMsg.SERVER_ERROR);
        }
        //拿到手机号和密码
        String mobile=loginVo.getMobile();
        String frompass=loginVo.getPassword();
        //判断手机号 是否存在
        MiaoshaUser miaoshaUser=getById(Long.parseLong(mobile));
        if (miaoshaUser==null){
            // return CodeMsg.MOBILE_NOT_EXIST;
            throw new GlobelException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass=miaoshaUser.getPassword();
        System.out.println(dbPass);
        String saltDB=miaoshaUser.getSalt();
        String calcPass=MD5Util.formPassToDbPass(frompass,saltDB);
        System.out.println(calcPass);
        if(!calcPass.equals(dbPass)){
           // return CodeMsg.PASSWORD_ERROR;
            throw new GlobelException(CodeMsg.PASSWORD_ERROR);
        }

        return true;

    }

    //把生成token给提出来
    public void addCookie(HttpServletResponse response,MiaoshaUser miaoshaUser){
//生成一个自定义cookie
        String token= UUIDUtil.UUID();
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        }
//从缓存中根据token取user
    public MiaoshaUser getByToken(HttpServletResponse response,String token) {

        if(token==null) {
            return null;
        }

          MiaoshaUser miaoshaUser=redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if(miaoshaUser!=null){
            addCookie(response,miaoshaUser);
        }

        return miaoshaUser;
    }

           /* public CodeMsg login(LoginVo loginVo){
                if(loginVo==null){
                   // return CodeMsg.SERVER_ERROR;
                    throw new GlobelException(CodeMsg.SERVER_ERROR);
                }
                //拿到手机号和密码
                String mobile=loginVo.getMobile();
                String frompass=loginVo.getPassword();
                //判断手机号 是否存在
                MiaoshaUser miaoshaUser=getById(Long.parseLong(mobile));
                if (miaoshaUser==null){
                   // return CodeMsg.MOBILE_NOT_EXIST;
                    throw new GlobelException(CodeMsg.MOBILE_NOT_EXIST);
                }
                //验证密码
                String dbPass=miaoshaUser.getPassword();
                String saltDB=miaoshaUser.getSalt();
                String calcPass=MD5Util.formPassToDbPass(frompass,saltDB);
                if(!calcPass.equals(dbPass)){
                    return CodeMsg.PASSWORD_ERROR;
                }
                return CodeMsg.SUCCESS;

            }*/

}
