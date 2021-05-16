package com.liaole.mall.shardingsphere.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_order")
public class Order implements Serializable {

    @TableId(type = IdType.AUTO)
    private long orderId; //主键

    private long userId; //用户id

    private BigDecimal totalPrice; //订单价格

    private int state ; //订单状态

    private LocalDateTime createTime; //创建时间

    private LocalDateTime updateTime; //修改时间
}
