package com.liaole.mall.search.service;

import com.liaole.mall.search.model.SeckillGoodsEs;

import java.util.List;

public interface SeckillGoodsSearchService {

    /**
     *  导入数据到es中
     * @param seckillGoodsEs
     */
    void add(SeckillGoodsEs seckillGoodsEs);

    /**
     *  根据活动ID搜索
     * @param acid
     * @return
     */
    List<SeckillGoodsEs> search(String acid);
}
