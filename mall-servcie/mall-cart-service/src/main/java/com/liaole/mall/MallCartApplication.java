package com.liaole.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.liaole.mall.goods.feign")
@MapperScan(basePackages = {"com.liaole.mall.cart.mapper"})
public class MallCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCartApplication.class,args);
    }
}
