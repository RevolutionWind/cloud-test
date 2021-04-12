package com.decent.order.utils;

import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.exceptions.ErrorCodeException;
import com.decent.common.pojo.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jiangy
 * @date 2021/1/2
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = ErrorCodeException.class)
    @ResponseBody
    public SimpleMessage myErrorHandler(HttpServletRequest request, ErrorCodeException e) {
        SimpleMessage message = new SimpleMessage();
        message.setErrorCode(e.getCode());
        message.setErrorMsg(e.getMessage());
        return message;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public SimpleMessage errorHandler(HttpServletRequest request, Exception ex) {
        SimpleMessage message = new SimpleMessage();
        log.error("[{}]系统异常", request.getRequestURI(), ex);
        message.setErrorCode(ErrorCodeEnum.ERROR.getCode());
        message.setErrorMsg(ErrorCodeEnum.ERROR.getMessage());
        return message;
    }
}
