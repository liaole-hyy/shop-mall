package com.liaole.mall.seckill.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class LogConsumerInterceptor implements ConsumerInterceptor<String,Object> {
    @Override
    public ConsumerRecords onConsume(ConsumerRecords<String,Object>  consumerRecords) {
        consumerRecords.forEach(record -> {
            log.info("将要消费消息为:--------->"+ JSON.toJSONString(record.value()));
        });
        return consumerRecords;
    }

    @Override
    public void close() {

    }

    @Override
    public void onCommit(Map map) {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
