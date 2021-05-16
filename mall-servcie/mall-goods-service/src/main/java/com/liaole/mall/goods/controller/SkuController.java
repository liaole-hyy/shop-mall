package com.liaole.mall.goods.controller;

import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.goods.model.Sku;
import com.liaole.mall.goods.service.SkuService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     *  根据分类下的推广查询产品列表
     * @param id
     * @return
     */
    @GetMapping(value = "/aditems/type/{id}")
    public RespResult typeItems(@PathVariable(value = "id") Integer id){
        //查询
        List<Sku> adSkuItems = skuService.typeSkuItems(id);
        return  RespResult.ok(adSkuItems);
    }

    @GetMapping(value = "/aditems/type")
    public RespResult typeItemsRequest(@RequestParam(value = "id") Integer id){
        //查询
        List<Sku> adSkuItems = skuService.typeSkuItems(id);
        return  RespResult.ok(adSkuItems);
    }

    /***
     * * 删除指定分类下的推广产品列表
     * */
    @DeleteMapping(value = "/aditems/type")
    public RespResult deleteTypeItems(@RequestParam(value = "id") Integer id){
        //清理缓存
        skuService.delTypeSkuItems(id);
        return RespResult.ok();
    }

    /****
     * 根据推广分类查询推广产品列表
     *
     */
    @PutMapping(value = "/aditems/type/{id}")
    public RespResult updateTypeItems(@PathVariable(value = "id")Integer id){
        //修改
        skuService.updateTypeSkuItems(id);
        return RespResult.ok();
    }

    /**
     * 根据ID获取Sku
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public RespResult<Sku> one(@PathVariable(value = "id") String id){
        Sku sku = skuService.getById(id);
        return RespResult.ok(sku);
    }

    /*** * 库存递减 * @param carts * @return */
    @PostMapping(value = "/dcount")
    public RespResult decount(@RequestBody List<Cart> carts){
        skuService.decount(carts);
        return RespResult.ok();
    }
}
