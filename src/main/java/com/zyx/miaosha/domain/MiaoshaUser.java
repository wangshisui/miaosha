package com.zyx.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Author:zhangyx
 * @Date:Created in 19:402018/11/14
 * @Modified By:
 */
@Data
public class MiaoshaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;


}
