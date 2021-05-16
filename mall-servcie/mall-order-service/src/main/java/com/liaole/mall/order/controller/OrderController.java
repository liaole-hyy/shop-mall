package com.liaole.mall.order.controller;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.liaole.mall.order.model.Order;
import com.liaole.mall.order.model.OrderRefund;
import com.liaole.mall.order.pay.WeixinPayParam;
import com.liaole.mall.order.service.OrderService;
import com.liaole.mall.pay.feign.PayFeign;
import com.liaole.mall.util.RespCode;
import com.liaole.mall.util.RespResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WeixinPayParam weixinPayParam;

    @Autowired
    private PayFeign payFeign;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /*** * 添加订单 */
    @PostMapping
    public RespResult add(@RequestBody Order order, HttpServletRequest request) throws Exception {
        String userName = "liaole";
        order.setUsername(userName);
        order.setCreateTime(new Date());
        order.setUpdateTime(order.getCreateTime());
        order.setId(IdWorker.getIdStr());
        order.setOrderStatus(0);
        order.setPayStatus(0);
        order.setIsDelete(0);
        //添加订单
        Boolean bo = orderService.add(order);

        //支付信息组装
        if(bo ){
            //加密后的字符串
            String cipText =weixinPayParam.weixinParam(order,request);
            // 模拟调用支付模块
            payFeign.pay(cipText);
            RespResult.ok(cipText);
        }

        return RespResult.error(RespCode.SYSTEM_ERROR);
    }

    @PutMapping(value = "/refund/{id}")
    public RespResult refund(@PathVariable(value = "id")String id,HttpServletRequest request) throws Exception {
        String userName = "liaole" ;

        //查询商品信息
        Order order = orderService.getById(id);

        //判断状态 已支付,待发货,才允许取消订单
        if( order.getOrderStatus().intValue() == 1  && order.getPayStatus().intValue() ==1 ){
            //退款记录
            OrderRefund orderRefund = new OrderRefund(
                    IdWorker.getIdStr(),
                    order.getId(),
                    0,  // 0整个订单退款，1单个明细退款
                    null,
                    userName,
                    0, //0 申请退款 1退款成功 2退款失败
                    new Date(),
                    order.getMoneys()
            );
            //发送事务消息
            Message message = MessageBuilder.withPayload(weixinPayParam.weixinRefundParam(orderRefund)).build();
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("refundtx", "refund", message, orderRefund);

            if(transactionSendResult.getSendStatus()== SendStatus.SEND_OK){
                return RespResult.ok();
            }
        }
        //不符合直接返回错误
        return  RespResult.error("当前订单不符合取消操作要求！");
    }
}
