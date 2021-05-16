package com.liaole.mall.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.seckill.mapper.SeckillGoodsMapper;
import com.liaole.mall.seckill.mapper.SeckillOrderMapper;
import com.liaole.mall.seckill.model.SeckillOrder;
import com.liaole.mall.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements SeckillOrderService {

    //库存不足
    public static final int STORE_NOT_FULL=0;
    //库存足够下单成功
    public static final int STORE_FULL_ORDER_SUCCESS=1;


    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsMapper seckillGoodsMapper;


    /***
     * 热门商品抢单实现
     * @return
     */
    @Override
    public int add(Map<String,Object> dataMap) {
      return 0;
    }
}
