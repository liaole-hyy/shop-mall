package com.liaole.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liaole.mall.order.model.Order;
import com.liaole.mall.order.model.OrderRefund;
import org.springframework.stereotype.Service;

public interface OrderService extends IService<Order> {

    //添加订单
    Boolean add(Order order);

    /**
     * 申请退款（取消订单）
     * @param orderRefund
     * @return
     */
    int refund(OrderRefund orderRefund);

    /**
     *  退款成功
     * @param out_trade_no   原始订单号
     * @param out_refund_no  退款记录订单号
     */
    void updateRefundStatus(String out_trade_no,String out_refund_no);

    /**
     *  退款失败 （退款失败不需要更改订单状态）
     * @param out_refund_no  退款记录订单号
     */
    void updateRefundFailStatus(String out_refund_no);
}
