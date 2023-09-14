package io.github.cnsukidayo.wword.core.receive;

import com.rabbitmq.client.Channel;
import io.github.cnsukidayo.wword.core.service.PostService;
import io.github.cnsukidayo.wword.mq.constant.MQConst;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author sukidayo
 * @date 2023/7/20 15:33
 */
@Component
public class PostReceiver {

    private final PostService postService;

    public PostReceiver(PostService postService) {
        this.postService = postService;
    }

    @RabbitListener(bindings = @QueueBinding(
        exchange = @Exchange(value = MQConst.EXCHANGE_POST_DIRECT),
        value = @Queue(value = MQConst.QUEUE_PUBLISH_POST, durable = "true"),
        key = {MQConst.ROUTING_PUBLISH_POST}
    ))
    public void publishPost(String postUrl, Message message, Channel channel) throws IOException {
        Assert.notNull(postUrl, "postUrl must not be null");
        System.out.println(Thread.currentThread() + "接收消息成功!" + postUrl);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
