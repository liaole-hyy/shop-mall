package com.liaole.mall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liaole.mall.cart.feign.CartFeign;
import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.goods.feign.SkuFeign;
import com.liaole.mall.order.mapper.OrderMapper;
import com.liaole.mall.order.mapper.OrderRefundMapper;
import com.liaole.mall.order.mapper.OrderSkuMapper;
import com.liaole.mall.order.model.Order;
import com.liaole.mall.order.model.OrderRefund;
import com.liaole.mall.order.model.OrderSku;
import com.liaole.mall.order.service.OrderService;
import com.liaole.mall.util.RespResult;
import io.seata.spring.annotation.GlobalTransactional;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private  OrderMapper orderMapper;

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private OrderRefundMapper orderRefundMapper;
    /**
     *  下单操作
     *         //1.查询购物车记录
     *         //2.库存递减 20000 成功
     *         //3.增加订单明细
     *         //4.增加订单
     *         //5.删除购物车记录
     * @param order
     * @return
     */
    @GlobalTransactional
    @Override
    public Boolean add(Order order) {
        //1.查询购物车记录
        RespResult<List<Cart>> cartResp = cartFeign.list(order.getCartIds());
        List<Cart> carts = cartResp.getData();
        if(carts.size()==0){
            return false;
        }
        //2.库存递减 20000 成功
        skuFeign.decount(carts);
        //3.增加订单明细
        int totlNum = 0; //商品个数
        int payMoney = 0; //支付总金额
        for (Cart cart : carts) {
            //类型转换
            OrderSku orderSku = JSON.parseObject(JSON.toJSONString(cart), OrderSku.class);
            orderSku.setId(IdWorker.getIdStr());
            orderSku.setMoney(orderSku.getPrice()*orderSku.getNum());
            orderSku.setSkuId(cart.getSkuId());
            orderSku.setOrderId(order.getId());
            orderSkuMapper.insert(orderSku);
            //统计数据
            totlNum+=cart.getNum();
            payMoney+=orderSku.getMoney();
        }

        //4.增加订单
        order.setTotalNum(totlNum);
        order.setMoneys(payMoney);
        order.setEndTime(new Date());
        orderMapper.insert(order);

        //Exception--->TestTransaction
        //int q=10/0;

        //5.删除购物车记录
        cartFeign.delete(order.getCartIds());
        return true;
    }

    /**
     * 申请退款操作（取消订单）
     * @param orderRefund
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int refund(OrderRefund orderRefund) {
        // 退款申请记录
        int icount = orderRefundMapper.insert(orderRefund);

        //订单状态变更
        Order order =  new Order();
        order.setOrderStatus(4);  //申请退款
        // 条件
        QueryWrapper<Order> queryWrapper = new QueryWrapper<Order>();
        queryWrapper.eq("id",orderRefund.getOrderNo());
        queryWrapper.eq("username",orderRefund.getUsername());
        //原来是已支付待发货的状态
        queryWrapper.eq("order_status",1);  //待发货
        queryWrapper.eq("pay_status",1); //已支付
        int mcount = orderMapper.update(order,queryWrapper);
        // 模拟异常
        //int  count = 10 / 0;
        return mcount;
    }

    /**
     *  退款处理成功
     * @param out_trade_no   原始订单号
     * @param out_refund_no  退款记录订单号
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRefundStatus(String out_trade_no, String out_refund_no) {
        //订单状态更新
        Order order = new Order();
        order.setId(out_refund_no);
        order.setOrderStatus(5);  //退款申请成功
        orderMapper.updateById(order);

        //修改退款记录状态
        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setId(out_refund_no);
        orderRefund.setStatus(2);  //退款申请成功，等待微信退款
        orderRefundMapper.updateById(orderRefund);
    }

    /**
     *  退款处理失败
     * @param out_refund_no  退款记录订单号
     */
    @Override
    public void updateRefundFailStatus(String out_refund_no) {
        OrderRefund orderRefund  = new OrderRefund();
        orderRefund.setId(out_refund_no);
        orderRefund.setStatus(1);  //申请退款失败
        orderRefundMapper.updateById(orderRefund);
    }

    public static void main(String[] args) {
        Order order =new Order();
        order.setPayType("1");
        order.setId("123456");
        order.setPayTime(new Date());
        order.setCartIds(new ArrayList<String>(Arrays.asList(new String[]{"liaole1383759276839641089", "liaole1385833616896798722"})));
        order.setConsignTime(new Date());
        order.setRecipients("廖乐");
        order.setRecipientsMobile("18872258932");
        order.setRecipientsAddress("湖北武汉");


        System.out.println(JSON.toJSONString(order));
    }
}
