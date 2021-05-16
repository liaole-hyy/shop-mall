package com.liaole.mall.seckill.controller;


import com.liaole.mall.seckill.model.SeckillActivity;
import com.liaole.mall.seckill.service.SeckillActivityService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/activity")
public class SeckillActivityController {

    @Autowired
    private SeckillActivityService seckillActivityService;

    /****
     * 有效活动列表
     * http://localhost:8092/test/lock
     */
    @GetMapping
    public RespResult list(){
        //查询活动列表
        List<SeckillActivity> seckillActivities = seckillActivityService.validActivity();
        System.out.println(seckillActivities);
        return RespResult.ok(seckillActivities);
    }

    @GetMapping(value = "test")
    public RespResult list1(){
        //查询活动列表
        SeckillActivity seckillActivity=new SeckillActivity();
        seckillActivity.setStartTime(new Date());
        seckillActivity.setEndTime(new Date());
        List<SeckillActivity> seckillActivities = new ArrayList<>();
        seckillActivities.add(seckillActivity);
        System.out.println(seckillActivities);
        return RespResult.ok(seckillActivities);
    }

    @PostMapping(value = "/add")
    public RespResult add(@RequestBody SeckillActivity seckillActivity ){
        if( seckillActivity == null ){
            return RespResult.error();
        }
        seckillActivity.setStartTime(new Date());
        seckillActivity.setEndTime( new Date(new Date().getTime() + 60*1000*30));
        seckillActivityService.save(seckillActivity);
        return RespResult.ok();
    }
}
