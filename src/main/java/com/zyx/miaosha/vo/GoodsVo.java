package com.zyx.miaosha.vo;

import com.zyx.miaosha.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @Author:zhangyx
 * @Date:Created in 11:202018/11/17
 * @Modified By:
 */
@Data
public class GoodsVo extends Goods {

    private Double miaoshaPrice;
    private Integer stockCount;

    private Date startDate;

    private Date endDate;

}
