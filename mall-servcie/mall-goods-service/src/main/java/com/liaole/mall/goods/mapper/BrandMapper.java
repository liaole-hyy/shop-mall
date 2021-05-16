package com.liaole.mall.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liaole.mall.goods.model.Brand;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {

    //根据分类id查询品牌id集合
    @Select("select brand_id from category_brand where category_id=#{id}")
    List<Integer> queryBrandIds(Integer id);
}
