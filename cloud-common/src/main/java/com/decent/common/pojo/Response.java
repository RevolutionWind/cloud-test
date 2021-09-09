package com.decent.common.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sunxy
 * @date 2019/7/2
 */
@Getter
@Setter
public class Response<T> {
    /**
     * 错误代码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 内容
     */
    private T data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
