package com.liaole.mall.canal.listener;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.liaole.mall.canal.job.dynamic.DynamicJob;
import com.liaole.mall.canal.job.dynamic.DynamicTaskCreate;
import com.liaole.mall.search.feign.SeckillGoodsSearchFeign;
import com.liaole.mall.search.model.SeckillGoodsEs;
import com.liaole.mall.seckill.model.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@CanalTable(value = "seckill_goods")
@Component
public class SeckillGoodsHandler implements EntryHandler<SeckillGoods> {

    @Autowired
    private SeckillGoodsSearchFeign seckillGoodsSearchFeign;

    @Autowired
    private DynamicTaskCreate dynamicTaskCreate;

    /**
     * 增加数据
     * @param seckillGoods
     */
    @Override
    public void insert(SeckillGoods seckillGoods) {
        //索引生成
        seckillGoodsSearchFeign.add(JSON.parseObject(JSON.toJSONString(seckillGoods), SeckillGoodsEs.class));
    }

    @Override
    public void update(SeckillGoods before, SeckillGoods after) {
        //索引生成
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String cron =sdf.format(new Date(new Date().getTime()+6000*5));
        dynamicTaskCreate.create(after.getId(),cron,1,new DynamicJob(),after.getActivityId());
        seckillGoodsSearchFeign.add(JSON.parseObject(JSON.toJSONString(after), SeckillGoodsEs.class));
    }

    @Override
    public void delete(SeckillGoods seckillGoods) {

    }
}
