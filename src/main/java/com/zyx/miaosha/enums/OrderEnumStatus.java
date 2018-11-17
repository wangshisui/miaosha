package com.zyx.miaosha.enums;

import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.annotations.Insert;

/**
 * @Author:zhangyx
 * @Date:Created in 13:172018/11/17
 * @Modified By:
 */
@Getter
public enum OrderEnumStatus  {
    STATUS_EMPTY(0,"商品为空")
    ;
    private Integer code;
    private String msg;

    OrderEnumStatus(Integer code, String msg){
       this.code=code;
        this.msg=msg;
    }

}
