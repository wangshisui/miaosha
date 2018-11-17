package com.zyx.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author:zhangyx
 * @Date:Created in 11:122018/11/17
 * @Modified By:
 */
@Data
public class OrderInfo {

    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
