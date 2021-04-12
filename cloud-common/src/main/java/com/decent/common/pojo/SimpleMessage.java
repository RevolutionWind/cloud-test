package com.decent.common.pojo;

import com.decent.common.enums.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;


/**
 * 接口简易返回信息
 *
 * @author wangyx
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuppressWarnings("unused")
public class SimpleMessage implements Serializable {
    private static final long serialVersionUID = -2957516153008725933L;
    private Integer errorCode;
    private String errorMsg;
    private Long expire;

    public SimpleMessage() {
    }

    public SimpleMessage(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SimpleMessage(ErrorCodeEnum errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getMessage();
    }

    public SimpleMessage(ErrorCodeEnum errorCode, String errorMsg) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorMsg;
    }

    public SimpleMessage(ErrorCodeEnum errorCode, String errorMsg, Long expire) {
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorMsg;
        this.expire = expire;
    }
}
