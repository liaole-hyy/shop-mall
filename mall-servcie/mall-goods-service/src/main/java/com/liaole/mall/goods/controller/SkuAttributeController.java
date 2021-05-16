package com.liaole.mall.goods.controller;

import com.liaole.mall.goods.model.SkuAttribute;
import com.liaole.mall.goods.service.SkuAttributeService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "sku")
public class SkuAttributeController {

    @Autowired
    private SkuAttributeService skuAttributeService ;

    /*** * 根据分类ID查询 */
    @GetMapping(value = "/category/{id}")
    public RespResult<SkuAttribute> categoryAttributeList(@PathVariable(value = "id")Integer id){
        //根据分类ID查询属性参数
        List<SkuAttribute> skuAttributes = skuAttributeService.queryList(id);
        return RespResult.ok(skuAttributes);
    }
}
