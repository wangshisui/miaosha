package com.zyx.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author:zhangyx
 * @Date:Created in 11:102018/11/17
 * @Modified By:
 */
@Data
public class MiaoshaGoods {

    private Long id;

    private Long goodsId;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}
