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
//加了对象缓存版本
public MiaoshaUser getById(Long id){

    //首先要取缓存
    MiaoshaUser user=redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
    if(!StringUtils.isEmpty(user)){
        return user;
    }
    //如果redis缓存中没有就从数据库中去取
    user=miaoshaUserDao.getById(id);
    if(user!=null){
        redisService.set(MiaoshaUserKey.getById,""+id,user);
    }
    return user;
    //return  miaoshaUserDao.getById(id);

}

//密码修改（进行update数据的场景，注意：1：先根据id去出来user然后将修改后的密码给一个新的user对象，
//  然后删除掉redis中原来的缓存，然后重新设置缓存）
    public boolean updatePasspotrd(String token,Long id,String password){
    //先根据id取user
       MiaoshaUser miaoshaUser= miaoshaUserDao.getById(id);
       if(miaoshaUser==null){
           throw new GlobelException(CodeMsg.MOBILE_NOT_EXIST);
       }
       MiaoshaUser toNewUser=new MiaoshaUser();
       toNewUser.setId(id);
       toNewUser.setPassword(MD5Util.formPassToDbPass(password,miaoshaUser.getSalt()));
        miaoshaUserDao.updateUser(toNewUser);

        //处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        miaoshaUser.setPassword(toNewUser.getPassword());
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        return true;
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
        String token=UUIDUtil.UUID();
        addCookie(response,token,miaoshaUser);
        return true;

    }

    //把生成token给提出来
    public void addCookie(HttpServletResponse response,String token,MiaoshaUser miaoshaUser){
//生成一个自定义cookie
        UUIDUtil.UUID();
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

    }
    //从缓存中根据token取user
    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        //延长token的有效期
        if(token==null) {

            return null;
        }

        MiaoshaUser miaoshaUser=redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if(miaoshaUser!=null){
            addCookie(response,token,miaoshaUser);
        }

        return miaoshaUser;
    }

//没有加缓存版本
  /* public MiaoshaUser getById(Long id){
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
          String token=UUIDUtil.UUID();
        addCookie(response,token,miaoshaUser);
        return true;

    }

    //把生成token给提出来
    public void addCookie(HttpServletResponse response,String token,MiaoshaUser miaoshaUser){
//生成一个自定义cookie
        UUIDUtil.UUID();
        redisService.set(MiaoshaUserKey.token,token,miaoshaUser);
        Cookie cookie=new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

        }
//从缓存中根据token取user
    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        //延长token的有效期
        if(token==null) {

            return null;
        }

          MiaoshaUser miaoshaUser=redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if(miaoshaUser!=null){
            addCookie(response,token,miaoshaUser);
        }

        return miaoshaUser;
    }
*/
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
