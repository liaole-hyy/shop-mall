package com.liaole.mall.search.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.liaole.mall.search.model.SeckillGoodsEs;
import com.liaole.mall.search.service.SeckillGoodsSearchService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/seckill/goods")
public class SeckillGoodsSearchController {

    @Autowired
    private SeckillGoodsSearchService seckillGoodsSearchService;

    /**
     * 导入数据到es
     */
    @PostMapping(value = "/add")
    public RespResult add(@RequestBody SeckillGoodsEs seckillGoodsEs){
        if(seckillGoodsEs == null){
            return RespResult.error("无秒杀数据可以导入es");
        }
        seckillGoodsSearchService.add(seckillGoodsEs);
        return RespResult.ok();
    }

    /**
     *  搜索商品数据
     * @param acid
     * @return
     */
    @GetMapping(value = "/search")
    public RespResult<List<SeckillGoodsEs>> list(@RequestParam("acid")String acid){
        //根据活动id搜索
        List<SeckillGoodsEs> seckillGoodsEsList = seckillGoodsSearchService.search(acid);
        return RespResult.ok(seckillGoodsEsList);
    }

    public static void main(String[] args) {
        SeckillGoodsEs es = new SeckillGoodsEs();
        es.setId(IdWorker.getIdStr());
        es.setActivityId("1");
        es.setSupId("1383757554054438914");
        es.setSkuId("1383759276839641089");
        es.setImages("1111.jpg");
        es.setName("华为荣耀pro");
        es.setPrice(100);
        es.setNum(985);
        es.setStoreCount(985);
        es.setSeckillPrice(10);
        es.setCreateTime(new Date());
        System.out.println(JSON.toJSONString(es));
    }
}
