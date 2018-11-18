package com.zyx.miaosha.vo;

import com.zyx.miaosha.domain.MiaoshaUser;
import lombok.Data;

/**
 * @Author:zhangyx
 * @Date:Created in 14:112018/11/18
 * @Modified By:
 */
@Data
public class GoodsDetailVo {

    private int miaoshaStatus;

    private int remainSeconds;

    private GoodsVo goodsVo;

    private MiaoshaUser miaoshaUser;
}
