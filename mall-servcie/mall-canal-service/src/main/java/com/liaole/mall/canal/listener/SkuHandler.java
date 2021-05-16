package com.liaole.mall.canal.listener;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.goods.model.Sku;
import com.liaole.mall.search.feign.SkuSearchFeign;
import com.liaole.mall.search.model.SkuEs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

@CanalTable(value = "sku")
@Component
public class SkuHandler implements EntryHandler<Sku> {

    @Autowired
    private SkuSearchFeign skuSearchFeign;

    /**
     * 新增
     * @param sku
     */
    @Override
    public void insert(Sku sku) {
        if(sku.getStatus().intValue() == 1){
            //导入索引
            SkuEs skuEs = JSON.parseObject(JSON.toJSONString(sku),SkuEs.class);
            skuSearchFeign.add(skuEs);
        }
    }

    /**
     * 修改
     * @param before
     * @param after
     */
    @Override
    public void update(Sku before, Sku after) {
        if(after.getStatus().intValue()==2){
            //删除索引
            skuSearchFeign.del(after.getId());
        }else{
            //更新
            skuSearchFeign.add(JSON.parseObject(JSON.toJSONString(after), SkuEs.class));
        }
    }

    /**
     *  删除监听
     * @param sku
     */
    @Override
    public void delete(Sku sku) {
        skuSearchFeign.del(sku.getId());
    }
}
