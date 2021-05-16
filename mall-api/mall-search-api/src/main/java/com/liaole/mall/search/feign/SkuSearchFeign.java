package com.liaole.mall.search.feign;

import com.liaole.mall.search.model.SkuEs;
import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mall-search")
public interface SkuSearchFeign {

    @PostMapping(value = "/search/add")
    RespResult add(@RequestBody SkuEs skuEs);

    @DeleteMapping(value = "/search/del/{id}")
    RespResult del(@PathVariable("id")String id);

}
