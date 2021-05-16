package com.liaole.mall.search.service.impl;

import com.liaole.mall.search.mapper.SeckillGoodsSearchMapper;
import com.liaole.mall.search.model.SeckillGoodsEs;
import com.liaole.mall.search.service.SeckillGoodsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillGoodsSearchServiceImpl implements SeckillGoodsSearchService {

    @Autowired
    private SeckillGoodsSearchMapper seckillGoodsSearchMapper;


    /**
     *  导入数据到 es 中
     * @param seckillGoodsEs
     */
    @Override
    public void add(SeckillGoodsEs seckillGoodsEs) {
        seckillGoodsSearchMapper.save(seckillGoodsEs);
    }

    /**
     *  根据活动ID搜索
     * @param acid
     * @return
     */
    @Override
    public List<SeckillGoodsEs> search(String acid) {
        return seckillGoodsSearchMapper.findByActivityIdOrderByCreateTimeDesc(acid);
    }
}
