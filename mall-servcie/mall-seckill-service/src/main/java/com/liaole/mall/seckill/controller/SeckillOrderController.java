package com.liaole.mall.seckill.controller;

import com.liaole.mall.seckill.service.SeckillOrderService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/seckill/order")
public class SeckillOrderController {

    @Autowired
    private SeckillOrderService seckillOrderService;

    /***
     * 抢单（非热门抢单）
     * @param username
     * @param id
     * @param num
     * @return
     */
    @PostMapping
    public RespResult add(String username, String id, Integer num){
        return RespResult.ok("抢单成功");
    }

}
