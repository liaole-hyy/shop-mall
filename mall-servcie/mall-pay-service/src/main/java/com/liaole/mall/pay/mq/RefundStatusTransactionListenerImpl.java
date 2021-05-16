package com.liaole.mall.pay.mq;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.liaole.mall.pay.mapper.RefundLogMapper;
import com.liaole.mall.pay.model.RefundLog;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@RocketMQTransactionListener(txProducerGroup = "refundstatustx")
@Component
public class RefundStatusTransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Autowired
    private RefundLogMapper refundLogMapper;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            //本地事务
            // 将附带参数 转换map
            Map<String,String>  resultMap = (Map<String, String>) o;
            //添加退款日志操作
            RefundLog refundLog = new RefundLog(
                    IdWorker.getIdStr(),
                    resultMap.get("out_trade_no"), //原订单号
                    resultMap.get("out_refund_no"), //退款订单号
                    Integer.valueOf(resultMap.get("refund_fee")), //退款金额
                    new Date()
            );
            int count = refundLogMapper.insert(refundLog);
            //本地事务结束
        }catch (Exception e){
            e.printStackTrace();
            return  RocketMQLocalTransactionState.ROLLBACK;
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
