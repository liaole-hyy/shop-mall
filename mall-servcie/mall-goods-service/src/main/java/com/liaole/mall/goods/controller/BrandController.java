package com.liaole.mall.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liaole.mall.goods.model.Brand;
import com.liaole.mall.goods.service.BrandService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 增加品牌
     */
    @PostMapping
    public RespResult add(@RequestBody Brand brand){
        //增加品牌
        brandService.save(brand);

        return RespResult.ok();
    }

    /**
     *  修改
     * @param brand
     * @return
     */
    @PutMapping
    public RespResult update(@RequestBody Brand brand){
        //修改品牌
        brandService.updateById(brand);
        return RespResult.ok();
    }

    @DeleteMapping("/{id}")
    public RespResult detele(@PathVariable(value = "id") Integer id){
        //删除品牌
        brandService.removeById(id);
        return RespResult.ok();
    }

    @PostMapping(value = "/list")
    public RespResult<List<Brand>> list(@RequestBody(required = false) Brand brand){
        //查询
        if(brand == null){
            brand = new Brand();
        }
        List<Brand> brands =brandService.queryList(brand);
        return RespResult.ok(brands);
    }

    @PostMapping(value = "/list/{page}/{size}")
    public RespResult list(@PathVariable(value = "page") Long currentPage,@PathVariable(value = "size")
            Long size,@RequestBody(required =false) Brand brand){
        if(brand == null) {
            brand = new Brand();
        }
        //分页查询
        Page<Brand> brandPage = brandService.queryPageList(currentPage,size,brand);
        return  RespResult.ok(brandPage);
    }

    /***
     * * 根据分类ID查询品牌
     */
    @GetMapping(value = "/category/{id}")
    public RespResult<List<Brand>> categoryBrands(@PathVariable(value = "id")Integer id){
        List<Brand> brands = brandService.queryByCategoryId(id);
        return RespResult.ok(brands);
    }
}
