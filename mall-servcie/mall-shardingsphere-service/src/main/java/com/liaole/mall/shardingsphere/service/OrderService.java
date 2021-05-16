package com.liaole.mall.shardingsphere.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liaole.mall.shardingsphere.model.Order;

public interface OrderService extends IService<Order> {

    public void saveTestTransaction(Order order,int random);
}
