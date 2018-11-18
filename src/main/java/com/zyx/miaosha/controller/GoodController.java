package com.zyx.miaosha.controller;

import com.zyx.miaosha.Result.Result;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.redis.GoodsKey;
import com.zyx.miaosha.redis.MiaoshaUserKey;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.service.GoodsService;
import com.zyx.miaosha.service.MiaoshaUserService;
import com.zyx.miaosha.vo.GoodsDetailVo;
import com.zyx.miaosha.vo.GoodsVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private  RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    ApplicationContext applicationContext;


//商品列表
@RequestMapping(value = "/to_list",produces = "text/html")
@ResponseBody
public String toLogin(HttpServletRequest request,HttpServletResponse response
                                        , Model model, MiaoshaUser user ){
    //return "goods_list";
    //先从缓存中去页面代码
    String html=redisService.get(GoodsKey.getGoodsList,"",String.class);
    //如果不是空就把取出来的页面直接返回去
    if(!StringUtils.isEmpty(html)){
        return html;
    }
    //第一步查询商品列表
    List<GoodsVo> goodsVoList=goodsService.getGoodsVoList();
    model.addAttribute("goodsList",goodsVoList);
    SpringWebContext swc=new SpringWebContext(request,response
           ,request.getServletContext(),request.getLocale(),model.asMap(),applicationContext);
    //手动渲染
    html=thymeleafViewResolver.getTemplateEngine().process("goods_list",swc);
    if(!StringUtils.isEmpty(html)){
        redisService.set(GoodsKey.getGoodsList,"",html);
    }
    return html;

}
//使用静态页面前后端分离的详情页
    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toDetail(HttpServletRequest request, HttpServletResponse response
            , Model model, MiaoshaUser user, @PathVariable("goodsId") Long goodsId){

        //得到商品id然后通过id获取商品的详情
        GoodsVo goodsVo=goodsService.getGoosVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);
        //获取一个秒杀开始时间
        long startAt=goodsVo.getStartDate().getTime();
        //获取一个秒杀结束时间
        long endAt=goodsVo.getEndDate().getTime();
        //获取当前时间
        long now=System.currentTimeMillis();
        int miaoshaStatus=0;//设置一个状态
        int remainSeconds=0;//还剩多少时间
        if(now<startAt){
            //秒杀还没有开始
            miaoshaStatus=0;
            remainSeconds= (int) ((startAt-now)/1000);
        }else if(now>endAt){
            //秒杀结束
            miaoshaStatus=2;
            remainSeconds=-1;
        }else {
            //秒杀进行时
            miaoshaStatus=1;
            remainSeconds=0;
        }

        //秒杀时的状态和时间传入到前端页面
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        GoodsDetailVo gdv0=new GoodsDetailVo();
        gdv0.setGoodsVo(goodsVo);
        gdv0.setMiaoshaUser(user);
        gdv0.setRemainSeconds(remainSeconds);
        gdv0.setMiaoshaStatus(miaoshaStatus);
        return Result.success(gdv0);
        // return "goods_detail";
    }
//使用了缓存的详情页
   /* @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request,HttpServletResponse response
                  ,Model model, MiaoshaUser user, @PathVariable("goodsId") Long goodsId){
    //首先取缓存
        String html=redisService.get(GoodsKey.getGoodsList,""+goodsId,String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //手动渲染
        model.addAttribute("user",user);
        //得到商品id然后通过id获取商品的详情
        GoodsVo goodsVo=goodsService.getGoosVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);
        //获取一个秒杀开始时间
         long startAt=goodsVo.getStartDate().getTime();
        //获取一个秒杀结束时间
        long endAt=goodsVo.getEndDate().getTime();
        //获取当前时间
        long now=System.currentTimeMillis();
        int miaoshaStatus=0;//设置一个状态
        int remainSeconds=0;//还剩多少时间
        if(now<startAt){
            //秒杀还没有开始
            miaoshaStatus=0;
            remainSeconds= (int) ((startAt-now)/1000);
        }else if(now>endAt){
            //秒杀结束
            miaoshaStatus=2;
            remainSeconds=-1;
        }else {
            //秒杀进行时
            miaoshaStatus=1;
            remainSeconds=0;
        }

        //秒杀时的状态和时间传入到前端页面
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        SpringWebContext swc=new SpringWebContext(request,response
                ,request.getServletContext(),request.getLocale(),model.asMap(),applicationContext);

        //手动渲染
        html=thymeleafViewResolver.getTemplateEngine().process("goods_detail",swc);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }
    return html;
       // return "goods_detail";
    }*/
    //没有使用缓存的详情页
 /*   @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request,HttpServletResponse response
            ,Model model, MiaoshaUser user, @PathVariable("goodsId") Long goodsId){

        //手动渲染


        model.addAttribute("user",user);
        //得到商品id然后通过id获取商品的详情
        GoodsVo goodsVo=goodsService.getGoosVoByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);
        //获取一个秒杀开始时间
        long startAt=goodsVo.getStartDate().getTime();
        //获取一个秒杀结束时间
        long endAt=goodsVo.getEndDate().getTime();
        //获取当前时间
        long now=System.currentTimeMillis();
        int miaoshaStatus=0;//设置一个状态
        int remainSeconds=0;//还剩多少时间
        if(now<startAt){
            //秒杀还没有开始
            miaoshaStatus=0;
            remainSeconds= (int) ((startAt-now)/1000);
        }else if(now>endAt){
            //秒杀结束
            miaoshaStatus=2;
            remainSeconds=-1;
        }else {
            //秒杀进行时
            miaoshaStatus=1;
            remainSeconds=0;
        }

        //秒杀时的状态和时间传入到前端页面
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        // return "goods_detail";
    }*/
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
