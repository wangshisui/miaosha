package com.zyx.miaosha.execption;

import com.zyx.miaosha.Result.CodeMsg;
import com.zyx.miaosha.Result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:zhangyx
 * @Date:Created in 21:492018/11/15
 * @Modified By:
 */
@ControllerAdvice
@ResponseBody
public class GlobleExectionHundler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHundler(HttpServletRequest request,Exception e){

        if(e instanceof GlobelException){
            GlobelException g= (GlobelException) e;
            return Result.error(g.getCm());
        }else if(e instanceof BindException){
            BindException exception= (BindException) e;
            List<ObjectError> errors=exception.getAllErrors();
            ObjectError msg=errors.get(0);
            String message=msg.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
