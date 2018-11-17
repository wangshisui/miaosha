package com.zyx.miaosha.service;

import com.zyx.miaosha.dao.Goodsdao;
import com.zyx.miaosha.domain.Goods;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.domain.OrderInfo;
import com.zyx.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:zhangyx
 * @Date:Created in 12:492018/11/17
 * @Modified By:
 */
@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

   //做秒杀的操作
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        //减少库存
        goodsService.reduceStock(goodsVo);
        //写订单
        return orderService.createOrder(user,goodsVo);



    }
}
