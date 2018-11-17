package com.zyx.miaosha.dao;

import com.zyx.miaosha.domain.Goods;
import com.zyx.miaosha.domain.MiaoshaGoods;
import com.zyx.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author:zhangyx
 * @Date:Created in 11:192018/11/17
 * @Modified By:
 */
@Mapper
public interface Goodsdao {

        @Select("select g.*,mg.stock_Count,mg.miaosha_price,mg.start_Date,mg.end_Date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
      public List<GoodsVo> getGoodsVoList();

    @Select("select g.*,mg.stock_Count,mg.miaosha_price,mg.start_Date,mg.end_Date from " +
            "miaosha_goods mg left join goods g on mg.goods_id=g.id where g.id=#{goodsId}")
    public GoodsVo getGoosVoByGoodsId(@Param("goodsId") Long goodsId);
    //减少库存
    @Update("update miaosha_goods set stock_count =stock_count-1 where goods_id=#{goodsId}")
    public void reduceStock(MiaoshaGoods g);
}
