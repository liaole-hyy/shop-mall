package com.liaole.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@MapperScan(basePackages = {"com.liaole.mall.user.mapper"})
@EnableFeignClients(basePackages = {"com.liaole.mall.user.fegin"})
public class MallUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class,args);
    }
}
