package com.liaole.mall.seckill.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    //定义topic
    public static final String topic_test= "test";

    //消费组
    public static final String TOPIC_GROUP1 = "topic.group1";

    public static final String TOPIC_GROUP2 = "topic.group2";


    /**
     * 发送消息
     * @param message
     */
    public void send(Object message){
        String obj2string = JSON.toJSONString(message);
        log.info("准备发送消息为: {}",obj2string);
        //发送消息
        ListenableFuture<SendResult<String,Object>> future = kafkaTemplate.send(topic_test,message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                //发送失败的处理
                log.info(topic_test + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //成功的处理
                log.info(topic_test + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }

}
