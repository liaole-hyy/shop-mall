package com.liaole.mall.goods.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//MybatisPlus的表映射注解
@TableName(value = "brand")
public class Brand implements Serializable {

    //品牌ID
    @TableId(type = IdType.AUTO)
    private Integer id ;

    // 品牌名字
    private String name ;
    // 品牌图片
    private String image ;
    // 品牌首字母
    private String initial ;
    // 品牌排序
    private Integer sort ;
}
