package com.zyx.miaosha.domain;

import lombok.Data;

/**
 * @Author:zhangyx
 * @Date:Created in 11:162018/11/17
 * @Modified By:
 */
@Data
public class MiaoshaOrder {

    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;
}
