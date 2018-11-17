package com.zyx.miaosha.domain;

import lombok.Data;

/**
 * @Author:zhangyx
 * @Date:Created in 11:082018/11/17
 * @Modified By:
 */
@Data
public class Goods {

    private Long id;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private String goodsDetail;

    private Double goodsPrice;

    private Integer goodsStock;
}
