package com.decent.integral.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 代理商积分查询
 *
 * @author sunxy
 * @date 2021/1/9 15:55
 */
@Mapper
public interface AgentIntegralDao {

    /**
     * 更新代理商积分
     *
     * @param agentId        代理商id
     * @param changeIntegral 改变的积分
     * @return 影响的行数
     */
    @Update("UPDATE agent_integral " +
            " SET agent_integral = agent_integral + #{changeIntegral} " +
            " WHERE agent_id = #{agentId} " +
            "FOR UPDATE")
    int updateAgentIntegral(@Param("agentId") String agentId, @Param("changeIntegral") Integer changeIntegral);

}
