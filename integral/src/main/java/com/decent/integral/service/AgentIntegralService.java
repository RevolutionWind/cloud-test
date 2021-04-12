package com.decent.integral.service;

import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;

/**
 * @author sunxy
 * @date 2021/1/2 16:18
 */
public interface AgentIntegralService {

    /**
     * 改变代理商的积分
     *
     * @param agentId         代理商id
     * @param changedIntegral 改变的积分
     * @return SimpleMessage
     */
    SimpleMessage changeAgentIntegral(String agentId, Integer changedIntegral);

    /**
     * 查询代理商积分
     *
     * @param agentId 代理商id
     * @return 结果
     */
    MessageBean getAgentIntegral(String agentId);

}
