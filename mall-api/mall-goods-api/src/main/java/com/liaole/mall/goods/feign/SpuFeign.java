package com.liaole.mall.goods.feign;

import com.liaole.mall.goods.model.Product;
import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(value = "mall-goods")  //微服务的名字
public interface SpuFeign {

    @GetMapping(value = "/spu/product/{id}")
    RespResult<Product> one(@PathVariable(value = "id")String id);

}
