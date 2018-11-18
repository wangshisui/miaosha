package com.zyx.miaosha.redis;

/**做页面缓存使用
 * @Author:zhangyx
 * @Date:Created in 20:242018/11/17
 * @Modified By:
 */
public class GoodsKey extends BasePrefix {



    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList=new GoodsKey(60,"gl");
    public static GoodsKey getGoodsDetail=new GoodsKey(60,"gd");
}
