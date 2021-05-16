package com.liaole.mall.cart.controller;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.cart.service.CartService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     *   添加购物车
     * @param id ：skuid
     * @param num
     * @return
     */
    @GetMapping(value = "/{id}/{num}")
    public RespResult add(@PathVariable(value = "id")String id,@PathVariable(value = "num")
                          Integer num){
        //用户名字
        String username = "liaole";
        //添加购物车
        cartService.add(id,username,num);
        return RespResult.ok();
    }

    /**** * 购物车列表 * @return */
    @GetMapping(value = "/list")
    public RespResult<List<Cart>> list(){
        String userName = "liaole";
        List<Cart> carts = cartService.list(userName);
        return RespResult.ok(carts);
    }

    /*** * 购物车数据 */
    @PostMapping(value = "/list")
    public RespResult<List<Cart>> list(@RequestBody List<String> ids){
        //购物车集合
        List<Cart> carts = cartService.list(ids);
        return RespResult.ok(carts);
    }

    /*** * 删除指定购物车 */
    @DeleteMapping
    public RespResult delete(@RequestBody List<String> ids){
        //删除购物车集合
        cartService.delete(ids);
        return RespResult.ok();
    }


    public static void main(String[] args) {
        List<String> ids = new ArrayList<>();
        ids.add("1111");
        ids.add("2222");
        System.out.println(JSON.toJSONString(ids));
    }
}
