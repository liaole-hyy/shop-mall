package com.liaole.mall.pay.mq;

import com.alibaba.fastjson.JSON;
import com.liaole.mall.pay.model.PayLog;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RocketMQMessageListener(topic = "order-pay-queue" ,consumerGroup = "orderpayqueue-consumer"
,selectorExpression = "*")
@Component
public class PayResultNotiferListener  implements RocketMQListener {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(Object message) {
        Map map = (Map) message;
        System.out.println("消息头:"+map.get("headers"));
        System.out.println("支付消息:"+ map.get("payload"));
        try {
            //String result = new String((byte[])map.get("payload"),"UTF-8") ;
            PayLog payLog = JSON.parseObject((String) map.get("payload"),PayLog.class);
            //处理支付成功通知
            Message<String> messageToOrder =  MessageBuilder.withPayload(JSON.toJSONString(payLog)).build();
            //发送收到支付成功事务消息,当前系统记录支付日志,支付日志记录成功则消息才会投递
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("rocket","log",messageToOrder,null);
            if(LocalTransactionState.COMMIT_MESSAGE.equals(transactionSendResult.getLocalTransactionState())){
                //处理成功
                System.out.println(transactionSendResult.getLocalTransactionState());
            }else{
                System.out.println(transactionSendResult.getLocalTransactionState());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
