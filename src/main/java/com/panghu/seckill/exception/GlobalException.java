package com.panghu.seckill.exception;


import com.panghu.seckill.result.CodeMsg;


/**
 * @author panghu
 */
public class GlobalException extends RuntimeException {

    private static final long servialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
