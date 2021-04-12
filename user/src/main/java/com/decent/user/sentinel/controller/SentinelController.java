package com.decent.user.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.pojo.SimpleMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 哨兵访问管理
 *
 * @author wangyx
 */
@RestController
public class SentinelController {
    /**
     * SentinelResource说明
     * value：Sentinel控制面板上的资源名
     * blockHandler：被限流降级时执行的方法
     * fallback：兜底异常处理方法
     *
     * @return SimpleMessage
     */
    @RequestMapping("/testSentinel")
    @SentinelResource(value = "testSentinel", blockHandler = "testHandleException", fallback = "testFallback")
    public SimpleMessage testSentinel() {
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    @SuppressWarnings("unused")
    public SimpleMessage testHandleException(BlockException exception) {
        return new SimpleMessage(ErrorCodeEnum.NO, exception.getLocalizedMessage());
    }

    @RequestMapping("/testNoLazy")
    @SentinelResource(value = "testNoLazy", blockHandler = "testHandleException")
    public SimpleMessage testNoLazy() {
        return new SimpleMessage(ErrorCodeEnum.OK, "testNoLazy");
    }
}
