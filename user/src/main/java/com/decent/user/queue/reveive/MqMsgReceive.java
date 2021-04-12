package com.decent.user.queue.reveive;

import com.alibaba.fastjson.JSONObject;
import com.decent.common.constant.integral.QueueKeyConstant;
import com.decent.common.mq.entity.TransactionsPriceCountDTO;
import com.decent.user.service.UserInfoService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Optional;

/**
 * @author : yum
 * @date : by 2021/1/5
 */
@Slf4j
@Component
public class MqMsgReceive {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 接受用户交易额变更的消息
     * 注: : 消息队列类的异常一般是相外抛出 Spring 调用ACK 就是判断该方法是否抛出异常
     *
     * @param message 消息实体
     * @param channel 渠道
     * @throws IOException 异常
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    // 队列名称
                    value = QueueKeyConstant.USER_TRANSACTIONS_QUEUE,
                    // 是否持久化
                    durable = "true"),
            exchange = @Exchange(
                    // 交换机名称 与 name属性 相同  名称尽量使用大写
                    value = QueueKeyConstant.ORDER_EXCHANGE,
                    // 忽略声明时异常 交换机名称相同但是属性不同声明的时候会出错  继续使用原来的
                    ignoreDeclarationExceptions = "true",
                    // 交换机的类型
                    type = ExchangeTypes.TOPIC
            ),
            // RoutingKey 路由key TOPIC类型可以使用通配符 * : 代表一个单词 # : 代表一个或多个单词
            // 使用RoutingKey来确认走哪个队列
            key = {QueueKeyConstant.USER_TRANSACTIONS_COUNT_KEY}))
    public void userTransactionsReceiver(String message, Channel channel) throws IOException {
        // 设置这个消费者每次只能处理一条消息
        channel.basicQos(1);
        log.info("mq -> userTransactionsReceiver: [{}]", message);
        if (StringUtils.isBlank(message)) {
            return;
        }
        Optional.ofNullable(JSONObject.parseObject(message, TransactionsPriceCountDTO.class))
                .ifPresent(userInfoService::changeUserTransaction);
    }
}
