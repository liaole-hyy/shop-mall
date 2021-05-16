package com.liaole.mall.pay.feign;

import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "mall-pay")
public interface PayFeign {

    @GetMapping(value = "/wx/pay")
    public RespResult<Map> pay(@RequestParam(value = "ciptext")String ciphertext) throws Exception;

}
