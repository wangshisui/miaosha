package com.zyx.miaosha.config;

import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.redis.MiaoshaUserKey;
import com.zyx.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:zhangyx
 * @Date:Created in 22:232018/11/16
 * @Modified By:
 */
@Service
public class UserArguementResover implements HandlerMethodArgumentResolver {
    @Autowired
    private MiaoshaUserService miaoshaUserService;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
       Class<?> clazz= methodParameter.getParameterType();
       return clazz== MiaoshaUser.class;

    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer
            , NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
          HttpServletRequest request=nativeWebRequest.getNativeRequest(HttpServletRequest.class);
          HttpServletResponse response=nativeWebRequest.getNativeResponse(HttpServletResponse.class);
          String paramtoken=request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
          String cookietoken=getCookieValue(request,MiaoshaUserService.COOKIE_NAME_TOKEN);
          if(paramtoken==null&&cookietoken==null){
              return null;
          }
        String token=StringUtils.isEmpty(paramtoken)?cookietoken:paramtoken;

        return  miaoshaUserService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies   =request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookieNameToken)){
                return cookie.getValue();
            }
        }
        return null;
    }

}
