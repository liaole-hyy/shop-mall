package com.liaole.mall.cart.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//MyBatisPlus表映射注解
@TableName(value = "cart")
public class Cart implements Serializable {

    private String id;       //主键
    @Column(name="username")
    @TableField(value = "username")
    private String userName; //用户名字
    private String name;     //商品名字
    private Integer price;   //单价
    private String image;    //商品图片
    private String skuId;   //商品ID
    private Integer num;    //商品数量
}
