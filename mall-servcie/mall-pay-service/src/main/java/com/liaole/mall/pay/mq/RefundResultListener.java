package com.liaole.mall.pay.mq;

import com.liaole.mall.pay.service.WeixinPayService;
import com.liaole.mall.util.Signature;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RocketMQMessageListener(topic = "refund",consumerGroup = "orderrefund-group")
@Component
public class RefundResultListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {

    @Autowired
    private WeixinPayService weixinPayService;

    @Autowired
    private Signature signature;




    @Override
    public void onMessage(Object o) {

    }

    /**
     * 监听退款申请操作
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                try {
                    for (MessageExt msg : msgs){
                        String result = new String(msg.getBody(),"UTF-8");
                        //数据解析,并验签
                        Map<String,String> map = signature.security(result);
                        if( map != null ){
                            //执行退款申请
                            Map<String,String> resultMap =  weixinPayService.refund(map);
                            System.out.println("退款申请resultMap:"+resultMap);
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
