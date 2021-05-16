package com.liaole.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = {"com.liaole.mall.goods.mapper"})
@EnableCaching //开启缓存
public class MallGoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallGoodsServiceApplication.class,args);
    }
}
