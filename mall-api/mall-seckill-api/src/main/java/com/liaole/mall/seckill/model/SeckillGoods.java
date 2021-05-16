package com.liaole.mall.seckill.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
//MyBatisPlus表映射注解
@TableName(value = "seckill_goods")
@Table
public class SeckillGoods {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @Column(name="sup_id")
    private String supId;
    @Column(name= "sku_id")
    private String skuId;
    private String name;
    private String images;
    private String content;
    private Integer price;
    @Column(name = "seckill_price")
    private Integer seckillPrice; //seckill_price
    private Integer num;
    @Column(name = "store_count")
    private Integer storeCount;
    @Column(name = "activity_id")
    private String activityId;
    //商品锁定状态   0 : 未锁定  1： 已锁定
    private Integer islock;
}
