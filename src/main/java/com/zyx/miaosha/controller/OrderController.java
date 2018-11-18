package com.zyx.miaosha.controller;

import com.zyx.miaosha.Result.CodeMsg;
import com.zyx.miaosha.Result.Result;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.domain.OrderInfo;
import com.zyx.miaosha.redis.RedisService;
import com.zyx.miaosha.service.GoodsService;
import com.zyx.miaosha.service.OrderService;
import com.zyx.miaosha.vo.GoodsDetailVo;
import com.zyx.miaosha.vo.GoodsVo;
import com.zyx.miaosha.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:zhangyx
 * @Date:Created in 16:482018/11/18
 * @Modified By:
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    RedisService redisService;
    @Autowired
    GoodsService goodsService;
    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> orderInfo(Model model, MiaoshaUser user, @Param("orderId") long orderId){

        if(user==null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo=orderService.getOrderById(orderId);
        if(orderInfo==null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId=orderInfo.getGoodsId();
        //拿到商品
        GoodsVo goods=goodsService.getGoosVoByGoodsId(goodsId);
        OrderDetailVo vo=new OrderDetailVo();
        vo.setGoods(goods);
        vo.setOrder(orderInfo);
        return Result.success(vo);
    }
}
