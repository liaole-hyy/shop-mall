package com.liaole.mall.shardingsphere.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.shardingsphere.mapper.OrderMapper;
import com.liaole.mall.shardingsphere.model.Order;
import com.liaole.mall.shardingsphere.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTestTransaction(Order order,int random) {
           this.save(order);
//           if(random % 2 == 0) {
//               int s = 10 / 0 ; //抛出异常
//           }
    }
}
