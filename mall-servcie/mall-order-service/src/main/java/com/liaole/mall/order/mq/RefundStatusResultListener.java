package com.liaole.mall.order.mq;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.order.service.OrderService;
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

import java.util.List;
import java.util.Map;

@RocketMQMessageListener(topic = "refundstatus" ,consumerGroup = "refundstatus-group")
@Component
public class RefundStatusResultListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Object o) {

    }

    /**
     * 消息监听 根据退款状态 更新退款订单
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for(MessageExt msg :msgs){
                        String result = new String( msg.getBody(),"UTF-8");
                        //获取对应参数判断状态 修改订单状态
                        Map<String,String> refundStatusMap = JSON.parseObject(result,Map.class);
                        String out_trade_no = refundStatusMap.get("out_trade_no");
                        String out_refund_no = refundStatusMap.get("out_refund_no");
                        if( "SUCCESS".equals(refundStatusMap.get("return_code")) &&
                        "SUCCESS".equals(refundStatusMap.get("result_code"))){
                            orderService.updateRefundStatus(out_trade_no,out_refund_no);
                        }else{
                            //退款失败
                            orderService.updateRefundFailStatus(out_refund_no);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
