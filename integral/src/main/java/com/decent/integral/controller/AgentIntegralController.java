package com.decent.integral.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.pojo.SimpleMessage;
import com.decent.integral.service.AgentIntegralService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author sunxy
 * @date 2021/1/2 16:13
 */
@RestController
@RequestMapping("/integral/agent")
public class AgentIntegralController {

    @Resource
    private AgentIntegralService agentIntegralService;

    /**
     * 改变代理商的积分
     *
     * @param agentId         代理商id
     * @param changedIntegral 改变的积分
     * @return SimpleMessage
     */
    @PostMapping("change")
    @SentinelResource(value = "change", fallback = "fallback", blockHandler = "blockHandler")
    public SimpleMessage changeAgentIntegral(String agentId, Integer changedIntegral) {
        if (StringUtils.isBlank(agentId)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "请选择代理商");
        }
        if (Objects.isNull(changedIntegral)) {
            return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "请选择改变的积分");
        }
        return agentIntegralService.changeAgentIntegral(agentId, changedIntegral);
    }

    public SimpleMessage fallback(String agentId, Integer changedIntegral) {
        return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "请选择改变的积分~~~~~");
    }

    public SimpleMessage blockHandler(BlockException e) {
        return new SimpleMessage(ErrorCodeEnum.INVALID_PARAMS, "blocking By Sentinel~~~~~");
    }


}
