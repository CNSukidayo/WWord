package io.github.cnsukidayo.wword.mq.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author sukidayo
 * @date 2023/7/15 15:48
 */
@Service
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 发送消息的方法
     *
     * @param exchange   交换机
     * @param routingKey 路由
     * @param message    消息
     * @return 返回是否发送成功的标识
     */
    public boolean sendMessage(String exchange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return true;
    }

}
