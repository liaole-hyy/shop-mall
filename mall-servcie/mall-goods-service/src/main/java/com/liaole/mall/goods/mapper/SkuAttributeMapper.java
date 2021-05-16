package com.liaole.mall.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liaole.mall.goods.model.SkuAttribute;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SkuAttributeMapper extends BaseMapper<SkuAttribute> {

    /**
     * 根据分类Id查询属性集合
     * @param id
     * @return
     */
    @Select("SELECT * FROM sku_attribute WHERE id IN(SELECT attr_id FROM category_attr WHERE category_id=#{id})")
    List<SkuAttribute> queryByCategoryId(Integer id);
}
