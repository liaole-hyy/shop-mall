package com.liaole.mall.order.mq;

import com.liaole.mall.order.model.OrderRefund;
import com.liaole.mall.order.service.OrderService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@RocketMQTransactionListener(txProducerGroup = "refundtx")
@Component
public class RefundTransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Autowired
    private OrderService orderService;


    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try{
            //-----------------本地事务执行操作开始
            //修改本地状态
            int count = orderService.refund((OrderRefund) o);
            if( count <= 0){
                return RocketMQLocalTransactionState.ROLLBACK;
            }
            //-----------------本地事务执行操作结束
        }catch (Exception e){
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 消息回查
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return RocketMQLocalTransactionState.COMMIT;
    }
}
