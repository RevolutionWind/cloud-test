package com.decent.order.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : yum
 * @date : by 2021/1/5
 * @description : 交易额信息更改
 */
@Component
public class MqSendProducer {

    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息
     *
     * @param mqKey   消息队列的key
     * @param jsonStr 消息
     */
    public void sendMessage(String mqKey, String jsonStr) {
        // 参数1: 交换机 参数2: routingKey 参数3: 消息
        amqpTemplate.convertAndSend(mqKey, jsonStr);
    }

}
