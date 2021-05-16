package com.liaole.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = {"com.liaole.mall.order.mapper"})
@EnableFeignClients(basePackages = {"com.liaole.mall.goods.feign",
        "com.liaole.mall.cart.feign","com.liaole.mall.pay.feign"})
public class MallOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class,args);
    }
}
