package com.decent.common.pojo;

import com.decent.common.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回信息
 *
 * @author sunxy
 */
@Data
public class MessageBean implements Serializable {
    private static final long serialVersionUID = 7192766535561421181L;
    private String errorMsg;
    private Object data;
    private Integer errorCode;

    public MessageBean() {
    }

    public MessageBean(Integer errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public MessageBean(Integer errorCode, String errorMsg, Object data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public MessageBean(Object data) {
        this.errorCode = 200;
        this.errorMsg = "请求通过";
        this.data = data;
    }

    public MessageBean(ErrorCodeEnum errorCodeEnum) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMsg = errorCodeEnum.getMessage();
    }

    public MessageBean(ErrorCodeEnum errorCodeEnum, String errorMsg) {
        this.errorCode = errorCodeEnum.getCode();
        this.errorMsg = errorMsg;
    }
}