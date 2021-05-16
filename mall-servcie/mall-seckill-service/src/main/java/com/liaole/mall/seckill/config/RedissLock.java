package com.liaole.mall.seckill.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissLock {

    @Bean
    public RedissonClient redissonClient(){
        //创建Config
        Config config = new Config();
        //集群实现
        //config.useClusterServers().setScanInterval(2000).addNodeAddress("xxxx");
        config.useSingleServer().setAddress("redis://192.168.5.128:6379");
        //创建RedissonClient客户端
        return Redisson.create(config);
    }

}
