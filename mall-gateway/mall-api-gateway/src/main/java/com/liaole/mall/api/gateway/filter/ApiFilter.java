package com.liaole.mall.api.gateway.filter;

import com.liaole.mall.api.gateway.permission.AuthorizationInterceptor;
import com.liaole.mall.api.gateway.util.IPUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 *  全局网关过滤
 */
@Component
public class ApiFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if(request.getPath().value().equals("/mall/user/login")){
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("token");
        Map<String,Object> resultMap = AuthorizationInterceptor.jwtVerify(token,  IPUtils.getIpAddr(request));
        if(resultMap == null){
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            exchange.getResponse().setComplete();
            exchange.getResponse().getHeaders().add("message","token check error");
            return chain.filter(exchange);
        }
        //用户名
        String username = "gp";
        //商品ID
        String id = request.getQueryParams().getFirst("id");
        //数量
        Integer num =Integer.valueOf( request.getQueryParams().getFirst("num") );

        //做操作
        //NOT_HOT 直接由后端服务处理
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
