package com.liaole.mall.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liaole.mall.goods.model.Sku;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SkuMapper  extends BaseMapper<Sku> {

    //εΊε­ιε
    @Update("UPDATE sku SET num=num-#{num} WHERE id=#{id} AND num>=#{num}")
    int decount(@Param("id") String skuId, @Param("num") Integer num);
}
