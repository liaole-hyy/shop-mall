package com.liaole.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.goods.model.Sku;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SkuService extends IService<Sku> {

    /**
     * 根据推广产品分类id查询Sku列表
     * @param id
     * @return
     */
    List<Sku> typeSkuItems(Integer id);

    /*** * 清理分类ID下的推广产品
     * @param id
     */
    void delTypeSkuItems(Integer id);

    List<Sku> updateTypeSkuItems(Integer id);

    /**
     *  库存递减
     * @param carts
     */
    void decount(List<Cart> carts);
}
