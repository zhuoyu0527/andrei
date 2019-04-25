package com.telfa.andrei.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaHelper {
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaHelper.class);
    private static KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 注入KafkaTemplate
     * @param kafkaTemplate kafka模版类
     */
    @Autowired
    public KafkaHelper(KafkaTemplate<String, String> kafkaTemplate) {
        if(KafkaHelper.kafkaTemplate == null) {
            KafkaHelper.kafkaTemplate = kafkaTemplate;
        }
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 异步发送消息, 如果返回值不需要处理, 传递callback参数为null, 如果是同步发送, 调用时加get(), 如下
     * <pre>
     *     KafkaHelper.sendMessage(topic, key, value, new ListenableFutureCallback<SendResult<String, String>>).get(); // 同步发送消息
     *     KafkaHelper.sendMessage(topic, key, value, null); // 不需要处理返回值
     * </pre>
     *
     * @param topic 主题
     * @param key 消息key
     * @param value 消息值
     * @param callback 回调方法
     */
    public static ListenableFuture<SendResult<String, String>> sendMessage(String topic, String key, String value, ListenableFutureCallback<SendResult<String, String>> callback) {
        LOGGER.info("异步发送消息: topic[{}], key[{}], value[{}].", topic, key, value);

        CompletableFuture.supplyAsync(() -> {
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, value);
            if(callback != null) {
                LOGGER.info("消息 topic[{}], key[{}] 有回调方法.", topic, key);
                future.addCallback(callback);
            }
            LOGGER.info("异步发送消息完毕, topic[{}], key[{}], value[{}].", topic, key, value);
            return future;
        });

        return null;
    }
}
