package com.zyx.miaosha.execption;

import com.zyx.miaosha.Result.CodeMsg;
import lombok.Data;

/**定义一个全局的异常
 * @Author:zhangyx
 * @Date:Created in 22:002018/11/15
 * @Modified By:
 */
@Data
public class GlobelException extends RuntimeException{

    private static final long serialVersionUID=1L;

     private CodeMsg cm;
    public GlobelException(CodeMsg cm){

         super(cm.toString());
         this.cm=cm;
    }
}
