package com.liaole.mall.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "ad_items")
public class AdItems {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer type;

    private String skuId;

    private Integer sort;
}
