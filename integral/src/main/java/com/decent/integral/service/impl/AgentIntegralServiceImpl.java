package com.decent.integral.service.impl;

import com.decent.common.enums.ErrorCodeEnum;
import com.decent.common.pojo.MessageBean;
import com.decent.common.pojo.SimpleMessage;
import com.decent.integral.dao.AgentIntegralDao;
import com.decent.integral.service.AgentIntegralService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author sunxy
 * @date 2021/1/2 16:18
 */
@Service
public class AgentIntegralServiceImpl implements AgentIntegralService {

    @Resource
    private AgentIntegralDao agentIntegralDao;

    /**
     * 改变代理商的积分
     *
     * @param agentId         代理商id
     * @param changedIntegral 改变的积分
     * @return SimpleMessage
     */
    @Override
    public SimpleMessage changeAgentIntegral(String agentId, Integer changedIntegral) {
        if (agentIntegralDao.updateAgentIntegral(agentId, changedIntegral) < 1) {
            return new SimpleMessage(ErrorCodeEnum.NO, "更新失败");
        }
        return new SimpleMessage(ErrorCodeEnum.OK);
    }

    /**
     * 查询代理商积分
     *
     * @param agentId 代理商id
     * @return 结果
     */
    @Override
    public MessageBean getAgentIntegral(String agentId) {
        return null;
    }
}
