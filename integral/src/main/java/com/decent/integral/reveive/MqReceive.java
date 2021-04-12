package com.decent.integral.reveive;

import com.decent.common.constant.integral.QueueKeyConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : yum
 * @date : by 2021/1/5
 * @description : 交易额变更
 */
@Slf4j
@Component
public class MqReceive {

    /**
     * 接受消息
     * 注: : 消息队列类的异常一般是相外抛出 Spring 调用ACK 就是判断该方法是否抛出异常
     *
     * @param message 消息实体
     * @param channel 渠道
     * @throws IOException 异常
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueKeyConstant.USER_INTEGRAL_QUEUE, durable = "true"),
            exchange = @Exchange(
                    value = QueueKeyConstant.ORDER_EXCHANGE,
                    ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {QueueKeyConstant.USER_INTEGRAL_ROUTING_KEY}))
    public void receiveUserIntegral(String message, Channel channel) throws IOException {
        // 设置这个消费者每次这能处理一条消息
        channel.basicQos(1);
        log.info("mq -> receiveUserIntegral receiver = [{}]", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        System.out.println(message);
    }

    /**
     * 接受消息
     * 注: : 消息队列类的异常一般是相外抛出 Spring 调用ACK 就是判断该方法是否抛出异常
     *
     * @param message 消息实体
     * @param channel 渠道
     * @throws IOException 异常
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QueueKeyConstant.USER_INTEGRAL_QUEUE, durable = "true"),
            exchange = @Exchange(
                    value = QueueKeyConstant.ORDER_EXCHANGE,
                    ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {QueueKeyConstant.AGENT_INTEGRAL_ROUTING_KEY}))
    public void receiveAgentIntegral(String message, Channel channel) throws IOException {
        // 设置这个消费者每次这能处理一条消息
        channel.basicQos(1);
        log.info("mq -> receiveAgentIntegral receiver = [{}]", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        System.out.println(message);
    }
}
