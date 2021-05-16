package com.liaole.mall.shardingsphere.controller;

import com.liaole.mall.shardingsphere.model.Order;
import com.liaole.mall.shardingsphere.service.OrderService;
import com.liaole.mall.util.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/shardsphere")
public class ShardSphere {

    @Autowired
    private OrderService orderService;

    /**
     *  测试水平分表插入
     * @return
     */
    @PostMapping(value = "/add")
    public RespResult testInsert(){
        for (int i = 0 ; i < 10; i++){
            Order order = new Order();
            order.setUserId(1L);
            order.setState(0);
            order.setTotalPrice(new BigDecimal((i + 1) * 5));
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(order.getCreateTime());
            //this.orderService.save(order);
           try {
               this.orderService.saveTestTransaction(order,i);
           }catch (Exception e){
               System.out.println("insert exception"+i);
           }

        }
        return RespResult.ok();
    }


    /**
     * 测试水平分表查询
     * @param id1
     * @param id2
     * @return
     */
    @GetMapping(value = "/select/{id1}/{id2}")
    public RespResult<List<Order>> testSelectList(@PathVariable(value = "id1") String id1,@PathVariable(value = "id2") String id2){
        List<Long> idList = Arrays.asList(Long.valueOf(id1),Long.valueOf(id2));
        List<Order> orderList = orderService.listByIds(idList);
        return RespResult.ok(orderList);
    }
}
