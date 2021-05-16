package com.liaole.mall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.cart.mapper.CartMapper;
import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.cart.service.CartService;
import com.liaole.mall.goods.feign.SkuFeign;
import com.liaole.mall.goods.model.Sku;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SkuFeign skuFeign;

    //加入购物车
    @Override
    public void add(String id, String userName, Integer num) {
        //删除购物车当前商品
        cartMapper.deleteById(userName+id);

        if(num > 0){
            //查询sku
            RespResult<Sku> skuRespResult = skuFeign.one(id);
            //将sku转换为cart

            Cart cart =new Cart();
            cart.setUserName(userName);
            cart.setSkuId(id);
            cart.setNum(num);
            sku2cart(skuRespResult.getData(),cart);
            //增加
            cartMapper.insert(cart);
        }

    }

    /**
     *  购物车列表
     * @param userName
     * @return
     */
    @Override
    public List<Cart> list(String userName) {
        //构造查询条件
        QueryWrapper<Cart> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",userName);

        return cartMapper.selectList(queryWrapper);
    }

    /**
     *  查询购物车列表
     * @param ids
     * @return
     */
    @Override
    public List<Cart> list(List<String> ids) {
        return cartMapper.selectBatchIds(ids);
    }

    /**
     *  删除ids
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        cartMapper.deleteBatchIds(ids);
    }

    /**
     *  Sku转Cart
     * @param sku
     * @param cart
     */
    public void sku2cart(Sku sku,Cart cart){
        cart.setImage(sku.getImage());
        cart.setId(cart.getUserName()+cart.getSkuId());
        cart.setName(sku.getName());
        cart.setPrice(sku.getPrice());
        cart.setSkuId(sku.getId());
    }
}
