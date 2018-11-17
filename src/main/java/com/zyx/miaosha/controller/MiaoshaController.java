package com.zyx.miaosha.controller;

import com.zyx.miaosha.Result.CodeMsg;
import com.zyx.miaosha.dao.Goodsdao;
import com.zyx.miaosha.domain.MiaoshaOrder;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.domain.OrderInfo;
import com.zyx.miaosha.service.GoodsService;
import com.zyx.miaosha.service.MiaoshaService;
import com.zyx.miaosha.service.OrderService;
import com.zyx.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author:zhangyx
 * @Date:Created in 12:342018/11/17
 * @Modified By:
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String doMiaosha(Model model, MiaoshaUser user, @RequestParam("goodsId") Long goodsId){

        model.addAttribute("user",user);
        if (user==null){
            return "login";
        }
     //先判断库存是否还存在
        GoodsVo goodsVo=goodsService.getGoosVoByGoodsId(goodsId);
        int stockCount=goodsVo.getStockCount();
        if(stockCount<=0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER);
            return "miaosha_fail";
        }
        //判断是不是已经秒杀过了
     MiaoshaOrder miaoshaOrder=orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(miaoshaOrder!=null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA);
            return "miaosha_fail";
        }
        //库存还有，也没有下过订单然后就是 减库存 下订单 写入秒杀订单
        //因为要满足事务的操作  所以 建一个单独的service

     OrderInfo orderInfo= miaoshaService.miaosha(user,goodsVo);

        //将秒杀后的订单信息直接写入页面
        model.addAttribute("orderInfo",orderInfo);
        //显示商品信息
        model.addAttribute("goods",goodsVo);

        return "order_detail";

    }

}
