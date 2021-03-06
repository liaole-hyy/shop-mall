package com.liaole.mall.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.seckill.mapper.SeckillActivityMapper;
import com.liaole.mall.seckill.model.SeckillActivity;
import com.liaole.mall.seckill.service.SeckillActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillActivityServiceImpl extends ServiceImpl<SeckillActivityMapper, SeckillActivity> implements SeckillActivityService {

    @Autowired
    private SeckillActivityMapper seckillActivityMapper;

    /***
     * 有效活动列表
     * @return
     */
    @Override
    public List<SeckillActivity> validActivity() {
        return seckillActivityMapper.validActivity();
    }
}
