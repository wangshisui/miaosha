package com.zyx.miaosha.vo;

import com.zyx.miaosha.domain.OrderInfo;
import lombok.Data;

/**
 * @Author:zhangyx
 * @Date:Created in 16:502018/11/18
 * @Modified By:
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;

    private OrderInfo order;
}
