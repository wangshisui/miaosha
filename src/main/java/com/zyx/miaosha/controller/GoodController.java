package com.zyx.miaosha.controller;

import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.redis.MiaoshaUserKey;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.service.GoodsService;
import com.zyx.miaosha.service.MiaoshaUserService;
import com.zyx.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

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
//商品列表
@RequestMapping("/to_list")
public String toLogin(Model model,MiaoshaUser user ){

    //第一步查询商品列表
    List<GoodsVo> goodsVoList=goodsService.getGoodsVoList();
    

    model.addAttribute("goodsList",goodsVoList);
    return "goods_list";
}

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, MiaoshaUser user, @PathVariable("goodsId") Long goodsId){

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
        return "goods_detail";
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
