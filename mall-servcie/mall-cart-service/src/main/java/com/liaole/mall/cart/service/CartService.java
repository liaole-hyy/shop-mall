package com.liaole.mall.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liaole.mall.cart.model.Cart;

import java.util.List;

public interface CartService extends IService<Cart> {

    /**
     *   加入购物车
     * @param id   :skuid
     * @param userName:用户名
     * @param num ： 加入购物车数量
     */
    void add(String id,String userName,Integer num);

    /**
     * 购物车列表
     * @param userName
     * @return
     */
    List<Cart> list(String userName);

    /**
     * 购物车列表
     * @param ids
     * @return
     */
    List<Cart> list(List<String> ids);

    /**
     * 删除购物车集合
     * @param ids
     */
    void delete(List<String> ids);

}
