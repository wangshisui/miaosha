package com.zyx.miaosha.validator;

import com.zyx.miaosha.vo.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author:zhangyx
 * @Date:Created in 21:382018/11/15
 * @Modified By:
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {


    private boolean required =false;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtil.isMobile(s);
        }else{
           if(StringUtils.isEmpty(s)){
               return true;
           }else {
               return ValidatorUtil.isMobile(s);
           }
        }

    }
}
