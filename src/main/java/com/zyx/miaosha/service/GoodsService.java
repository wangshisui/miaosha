package com.zyx.miaosha.service;

import com.zyx.miaosha.dao.Goodsdao;
import com.zyx.miaosha.domain.Goods;
import com.zyx.miaosha.domain.MiaoshaGoods;
import com.zyx.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:zhangyx
 * @Date:Created in 11:182018/11/17
 * @Modified By:
 */
@Service
public class GoodsService {

    @Autowired
   private Goodsdao goodsDao;

    public List<GoodsVo> getGoodsVoList(){
        return goodsDao.getGoodsVoList();
    }
   //根据id获取商品详情
    public GoodsVo getGoosVoByGoodsId(Long goodsId) {

        return goodsDao.getGoosVoByGoodsId(goodsId);
    }
  //减少库存
    public void reduceStock(GoodsVo goodsVo) {
        MiaoshaGoods g=new MiaoshaGoods();
      g.setGoodsId(goodsVo.getId());

        goodsDao.reduceStock(g);
    }
}
