package com.decent.common.constant.integral;

/**
 * 用户积分队列常量
 * <p>
 * <br/>
 *
 * @Author: sunxy
 * @create 2021/1/9 16:35
 */
public interface QueueKeyConstant {
    /**
     * 用户积分队列
     */
    String USER_INTEGRAL_QUEUE = "USER.INTEGRAL.QUEUE";
    /**
     * 用户积分监听RoutingKey
     */
    String USER_INTEGRAL_ROUTING_KEY = "USER.INTEGRAL";
    /**
     * 代理商返积分
     */
    String AGENT_INTEGRAL_ROUTING_KEY = "AGENT.INTEGRAL";
    /**
     * 用户交易额队列
     */
    String USER_TRANSACTIONS_QUEUE = "CHENG.QUAN.QUEUE";
    /**
     * 用户交易额交换机
     */
    String ORDER_EXCHANGE = "CHENG.QUAN.EXCHANGE";
    /**
     * 用户交易额监听RoutingKey
     */
    String USER_TRANSACTIONS_COUNT_KEY = "UserTransactionsCount";

}
