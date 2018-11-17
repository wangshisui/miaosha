package com.zyx.miaosha.dao;

import com.zyx.miaosha.domain.MiaoshaOrder;
import com.zyx.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Author:zhangyx
 * @Date:Created in 12:542018/11/17
 * @Modified By:
 */
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") Long userId,@Param("goodsId") Long goodsId);
    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status," +
            "create_date) values(#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status}," +
            "#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    Long insert(OrderInfo orderInfo);
     @Insert("insert into miaosha_order (user_id,goods_id,order_id) values(#{userId},#{goodsId},#{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
