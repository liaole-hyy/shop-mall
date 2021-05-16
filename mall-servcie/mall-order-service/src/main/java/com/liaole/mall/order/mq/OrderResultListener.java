package com.liaole.mall.order.mq;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.order.model.Order;
import com.liaole.mall.order.service.OrderService;
import com.liaole.mall.pay.model.PayLog;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Component
@RocketMQMessageListener(topic = "log",consumerGroup = "order-group")
public class OrderResultListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private OrderService orderService;

    /**
     * 监听消息
     * 实现RocketMQPushConsumerLifecycleListener监听器之后，此方法不调用
     * @param o
     */
    @Override
    public void onMessage(Object o) {

    }

    /**
     * 消息监听
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for(MessageExt msg : msgs){
                        String result = new String(msg.getBody(),"UTF-8");
                        System.out.println("consumer---------------result:"+result);
                        //根据支付结果，更新订单状态,并发送送金币给用户
                        if(! StringUtils.isEmpty(result) ) {
                            PayLog payLog = JSON.parseObject(result,PayLog.class);
                            Order order =new Order();
                            order.setId(payLog.getId());
                            order.setPayStatus(payLog.getStatus());
                            order.setOrderStatus(payLog.getStatus());
                            order.setPayTime(payLog.getCreateTime());
                            order.setWeixinTransactionId(payLog.getPayId());
                            orderService.updateById(order);
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    System.out.println("支付成功回调通知消息处理失败");
                }
                //消费状态
                return  ConsumeConcurrentlyStatus.CONSUME_SUCCESS   ;
            }
        });
    }
}
