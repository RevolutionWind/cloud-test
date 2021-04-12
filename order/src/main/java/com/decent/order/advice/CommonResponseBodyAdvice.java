package com.decent.order.advice;

import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author sunxy
 * @date 2021/1/23 14:15
 */
@SuppressWarnings("NullableProblems")
@Slf4j
@ControllerAdvice
public class CommonResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (body instanceof SimpleMessage || body instanceof MessageBean) {
            return body;
        }
        MessageBean result = new MessageBean();
        result.setErrorCode(ErrorCodeEnum.OK.getCode());
        result.setErrorMsg(ErrorCodeEnum.OK.getMessage());
        result.setData(body);
        return result;
    }
}
