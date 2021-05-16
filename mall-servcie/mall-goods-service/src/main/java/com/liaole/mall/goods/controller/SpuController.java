package com.liaole.mall.goods.controller;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.goods.model.Product;
import com.liaole.mall.goods.model.Sku;
import com.liaole.mall.goods.model.Spu;
import com.liaole.mall.goods.service.SpuService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @PostMapping(value = "/save")
    public RespResult save(@RequestBody Product product){
        if(product == null){
            product = new Product();
        }
        //保存
        spuService.saveProduct(product);
        return RespResult.ok();
    }

    /**
     * 查询product
     * @param id
     * @return
     */
    @GetMapping(value = "/product/{id}")
    public RespResult<Product> one(@PathVariable(value = "id")String id){
        Product product = spuService.findBySpuId(id);
        return RespResult.ok(product);
    }

    public static void main(String[] args){
//        Spu spu = new Spu();
//        spu.setName("华为pro");
//        spu.setIntro("华为pro xxxxxx1111");
//        spu.setBrandId(2);
//        spu.setCategoryThreeId(11179);
//        spu.setCategoryOneId(null);
//        spu.setCategoryTwoId(null);
//        spu.setImages("1111111.jpg");
//        spu.setContent("xxxxxxxxxxxxxx");
//        spu.setAttributeList("价格 规格 数量");
//
//
//        List<Sku> skus = new ArrayList<Sku>();
//        Sku sku = new Sku();
//        sku.setName("sku1");
//        sku.setPrice(100);
//        sku.setNum(1000);
//        sku.setImage("2222.jpg");
//        sku.setCategoryId(11188);
//        sku.setCategoryName("brand1");
//        sku.setSkuAttribute("价格 数量 规格");
//
//        skus.add(sku);
//        Product product = new Product();
//        product.setSpu(spu);
//        product.setSkus(skus);
//        System.out.println(JSON.toJSONString(product));
        Map<String,String> map = new HashMap<>();
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        System.out.println(JSON.toJSONString(map));

        String str ="{'商品数量':'数量','商品价格':价格'}";
        Map<String,String> map1 = JSON.parseObject(str, Map.class);
        System.out.println(map1);
    }
}
