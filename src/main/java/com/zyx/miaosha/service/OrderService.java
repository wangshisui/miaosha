package com.zyx.miaosha.service;

import com.zyx.miaosha.dao.OrderDao;
import com.zyx.miaosha.domain.MiaoshaOrder;
import com.zyx.miaosha.domain.MiaoshaUser;
import com.zyx.miaosha.domain.OrderInfo;
import com.zyx.miaosha.enums.OrderEnumStatus;
import com.zyx.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author:zhangyx
 * @Date:Created in 12:442018/11/17
 * @Modified By:
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
  //判断用户有没有秒杀到商品
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {

        return orderDao.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
    }
    //减少库存后生成订单
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setUserId(user.getId());
       // orderInfo.setOrderChannel(1);
        orderInfo.setOrderChannel(1);
        //orderInfo.setSataus(OrderEnumStatus.STATUS_EMPTY.getCode());
        orderInfo.setStatus(OrderEnumStatus.STATUS_EMPTY.getCode());
        orderInfo.setId(user.getId());
        //往数据库中添加订单信息
        Long orderId=orderDao.insert(orderInfo);
        //往秒杀订单表中写入数据
        MiaoshaOrder miaoshaOrder=new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
