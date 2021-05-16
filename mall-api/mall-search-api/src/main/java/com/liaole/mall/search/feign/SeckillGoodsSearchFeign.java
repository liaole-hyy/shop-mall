package com.liaole.mall.search.feign;

import com.liaole.mall.search.model.SeckillGoodsEs;
import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mall-search")
public interface SeckillGoodsSearchFeign {

    @PostMapping(value = "/seckill/goods/add")
    public RespResult add(@RequestBody SeckillGoodsEs seckillGoodsEs);
}
