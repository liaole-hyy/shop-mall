package com.liaole.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liaole.mall.goods.model.Product;
import com.liaole.mall.goods.model.Spu;

public interface SpuService extends IService<Spu> {

    //保存商品
    void saveProduct(Product product);

    Product findBySpuId(String id);

}
