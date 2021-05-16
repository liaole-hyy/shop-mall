package com.liaole.mall.search.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.liaole.mall.search.model.SkuEs;
import com.liaole.mall.search.service.SkuSearchService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/search")
public class SkuSearchController {

    @Autowired
    private SkuSearchService skuSearchService;

    /**
     * 单个商品导入
     * @param skuEs
     * @return
     */
    @PostMapping(value = "/add")
    public RespResult add(@RequestBody SkuEs skuEs){
        skuSearchService.add(skuEs);
        return RespResult.ok();
    }

    /**
     *  根据id删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/del/{id}")
    public RespResult del(@PathVariable("id")String id){
        skuSearchService.del(id);
        return RespResult.ok();
    }

    /**
     * 商品搜索
     * @param searchMap
     * @return
     */
    @GetMapping
    public RespResult<Map<String,Object>> search(@RequestParam Map<String,Object> searchMap){
        Map<String,Object> result = skuSearchService.search(searchMap);
        return RespResult.ok(result);
    }


    public static void main(String[] args) {
        SkuEs skuEs =new SkuEs();
        skuEs.setId("123456");
        skuEs.setName("华为荣耀pro");
        skuEs.setPrice(4888);
        skuEs.setNum(10000);
        skuEs.setImage("1111.jpg");
        skuEs.setImages("2.jpg,3.jpg");
        skuEs.setCreateTime(new Date());
        skuEs.setUpdateTime(new Date());
        skuEs.setSpuId("1383757554054438914");
        skuEs.setBrandId(2);
        skuEs.setBrandName("brand2");
        skuEs.setCategoryId(11179);
        skuEs.setCategoryName("华为2");
        skuEs.setSkuAttribute("{'商品数量':'数量','商品价格':'价格'}");
        skuEs.setStatus(1);
        System.out.println(JSON.toJSONString(skuEs));
    }
}
