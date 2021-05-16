package com.liaole.mall.goods.controller;

import com.liaole.mall.goods.model.Category;
import com.liaole.mall.goods.service.CategoryService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**** * 根据父ID查询子分类 */
    @GetMapping(value = "/parent/{pid}")
    public RespResult<List<Category>> list(@PathVariable(value = "pid")Integer pid){

        List<Category> categories = categoryService.queryByParentId(pid);
        return RespResult.ok(categories);
    }
}
