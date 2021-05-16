package com.liaole.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.goods.mapper.CategoryMapper;
import com.liaole.mall.goods.model.Category;
import com.liaole.mall.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private  CategoryMapper categoryMapper;
    /**
     *  根据父类id查询子分类
     * @param pid
     * @return
     */
    @Override
    public List<Category> queryByParentId(Integer pid) {
        //条件封装
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>();
        queryWrapper.eq("parent_id",pid);
        return categoryMapper.selectList(queryWrapper);
    }
}
