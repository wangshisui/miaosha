package com.zyx.miaosha.vo;

import com.zyx.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author:zhangyx
 * @Date:Created in 20:092018/11/14
 * @Modified By:
 */
@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min=32)
    private String password;
}
