package com.liaole.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.goods.mapper.AdItemsMapper;
import com.liaole.mall.goods.mapper.SkuMapper;
import com.liaole.mall.goods.model.AdItems;
import com.liaole.mall.goods.model.Sku;
import com.liaole.mall.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "ad-items-skus")
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    @Autowired
    private AdItemsMapper adItemsMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 根据推广产品分类id查询SKu列表
     * cacheNams = "ad-items-skus" :命名空间
     * key = "#id" :入参id作为缓存的key 使用spel表达式
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    @Override
    public List<Sku> typeSkuItems(Integer id) {
        //查询所有分类的下推广
        QueryWrapper<AdItems> queryAdItemsWrapper = new QueryWrapper<AdItems>();
        queryAdItemsWrapper.eq("type", id);
        List<AdItems> adItems = adItemsMapper.selectList(queryAdItemsWrapper);

        //获取所有的SkuId
        List<String> skuIds = adItems.stream().map(adItems1 -> adItems1.getSkuId())
                .collect(Collectors.toList());
        //批量查询Sku
        List<Sku> skus = skuMapper.selectBatchIds(skuIds);

        return skus;
    }

    @CacheEvict(key = "#id")
    @Override
    public void delTypeSkuItems(Integer id) {

    }

    /**
     * 修改缓存 ,当用这个方法，CachePut不会直接取缓存的值，而是去数据库重新查一遍
     *
     * @param id
     * @return
     */
    @CachePut(key = "#id")
    @Override
    public List<Sku> updateTypeSkuItems(Integer id) {
        //查询当前分类下所有列表信息
        QueryWrapper<AdItems> adItemsQueryWrapper = new QueryWrapper<AdItems>();
        adItemsQueryWrapper.eq("type", id);
        List<AdItems> adItems = adItemsMapper.selectList(adItemsQueryWrapper);

        //获取所有的SkuId
        List<String> skuIds = adItems.stream().map(adItems1 -> adItems1.getSkuId())
                .collect(Collectors.toList());
        return skuIds == null || skuIds.size() <= 0 ? null : skuMapper.selectBatchIds(skuIds);
    }

    /**
     * 库存递减
     *
     * @param carts
     */
    @Override
    public void decount(List<Cart> carts) {
        for (Cart cart : carts) {
            //语句级控制，防止超卖
            int count = skuMapper.decount(cart.getSkuId(), cart.getNum());
            if (count <= 0) {
                throw new RuntimeException("库存不足！");
            }
        }
    }
}