package com.decent.common.exceptions;


import com.decent.common.enums.ErrorCodeEnum;

/**
 * @author zhongzq
 */
@SuppressWarnings("unused")
public class ErrorCodeException extends RuntimeException {

    private static final long serialVersionUID = -7638041501183925225L;

    private Integer code;

    public ErrorCodeException(ErrorCodeEnum errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
    }

    public ErrorCodeException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ErrorCodeException(ErrorCodeEnum errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ErrorCodeException(String msg) {
        super(msg);
        this.code = ErrorCodeEnum.NO.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
