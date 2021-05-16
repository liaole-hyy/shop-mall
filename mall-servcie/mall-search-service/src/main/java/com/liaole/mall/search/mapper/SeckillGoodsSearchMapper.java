package com.liaole.mall.search.mapper;

import com.liaole.mall.search.model.SeckillGoodsEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SeckillGoodsSearchMapper extends ElasticsearchRepository<SeckillGoodsEs,String> {

    //根据ActivityId搜索数据
    List<SeckillGoodsEs> findByActivityIdOrderByCreateTimeDesc(String acid);
}
