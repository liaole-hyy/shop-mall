package com.liaole.mall.canal.job.dynamic;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicConfig {

    @Value("${dynamiczk}")
    private String dynamiczk;

    @Value("${dynamicnamespace}")
    private String dynamicnamespace;

    /**
     * 指定当前注册地址信息
     * @return
     */
    @Bean
    public ZookeeperConfiguration zookeeperConfiguration(){
        return  new ZookeeperConfiguration(dynamiczk,dynamicnamespace);
    }

    /**
     *  向Zookeeper服务注册
     * @param zookeeperConfiguration
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter zookeeperRegistryCenter(ZookeeperConfiguration zookeeperConfiguration){
        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        System.out.println(zookeeperRegistryCenter);
        System.out.println(zookeeperRegistryCenter.getClient());
        return zookeeperRegistryCenter ;
    }

}
