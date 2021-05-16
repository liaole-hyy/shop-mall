package com.liaole.mall.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageConfig {

    /**
     * 分页拦截器
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置数据类型
        paginationInterceptor.setDbType(DbType.MYSQL);
        System.out.println("---------分页拦截器" +paginationInterceptor);
        return  paginationInterceptor;
    }
}
