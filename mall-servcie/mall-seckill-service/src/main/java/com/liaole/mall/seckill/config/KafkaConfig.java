package com.liaole.mall.seckill.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableKafka
@ConditionalOnProperty(prefix = "spring.kafka",name="enabled",matchIfMissing = true)
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize ;
    @Value("${spring.kafka.producer.buffer-memory}")
    private int bufferMemory;
//    @Value("${spring.kafka.producer.transaction-id-prefix}")
//    private String transactionIdPrefix;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private boolean enableAutoCommit;
    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private String autoCommitInterval;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ProducerFactory<?,?> kafkaProducerFactory(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
        configs.put(ProducerConfig.ACKS_CONFIG,acks);
        configs.put(ProducerConfig.RETRIES_CONFIG,retries);
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG,batchSize);
        configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG,bufferMemory);
        //配置生产者拦截器
        List<String> intercept = new ArrayList<>();
        intercept.add("com.liaole.mall.seckill.mq.LogProducerInterceptor");
        configs.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,intercept);

        //设置系列化
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);

        DefaultKafkaProducerFactory<?,?> factory = new DefaultKafkaProducerFactory<>(configs);
//        if(!StringUtils.isEmpty(transactionIdPrefix)){
//            factory.setTransactionIdPrefix(transactionIdPrefix);
//        }
        return factory;
    }

    @Bean
    public ConsumerFactory<?,?> kafkaConsumerFactory(){
        Map<String,Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,servers);
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,enableAutoCommit);
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,autoCommitInterval);
        //configs.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,autoOffsetReset);

        //配置消费拦截器
        List<String> intercept = new ArrayList<>();
        intercept.add("com.liaole.mall.seckill.mq.LogConsumerInterceptor");
        configs.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,intercept);

        //设置序列化
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(configs);
    }
}
