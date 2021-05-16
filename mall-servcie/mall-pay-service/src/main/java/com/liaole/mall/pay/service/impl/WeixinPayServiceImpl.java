package com.liaole.mall.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.liaole.mall.pay.mapper.PayLogMapper;
import com.liaole.mall.pay.model.PayLog;
import com.liaole.mall.pay.service.WeixinPayService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Random;

@Service
public class WeixinPayServiceImpl implements WeixinPayService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private PayLogMapper payLogMapper;

    /**
     *  模拟微信退款申请
     * @param dataMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> refund(Map<String, String> dataMap) throws Exception {
        System.out.println("微信退款开始执行操作");
        //模拟申请退款成功  --》 发送退款申请状态通知
        dataMap.put("return_code","SUCCESS");
        dataMap.put("result_code","SUCCESS");
        System.out.println("执行成功:"+dataMap);
        //执行事务通知
        Message message = MessageBuilder.withPayload(JSON.toJSONString(dataMap)).build();
        rocketMQTemplate.sendMessageInTransaction("refundstatustx", "refundstatus", message, dataMap);
        return dataMap;
    }

    /**
     *   模拟微信预支付订单创建方法-获取支付地址
     * @param dataMap
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, String> preOrder(Map<String, String> dataMap) throws Exception {
        dataMap.put("orderNumver", IdWorker.getIdStr());
        dataMap.put("monet","10000.00");
        String contextJson = JSON.toJSONString(dataMap);
        // 模拟支付成功  -->发送支付通知消息
        PayLog payLog = new PayLog(dataMap.get("out_trade_no"),1,contextJson,dataMap.get("orderNumver"),new Date());
        Message<String> message =  MessageBuilder.withPayload(JSON.toJSONString(payLog)).build();
        rocketMQTemplate.convertAndSend("order-pay-queue",message);
        return dataMap ;
    }

    /**
     *  主动查询支付结果
     * @param outno  订单编号
     * @return
     * @throws Exception
     */
    @Override
    public PayLog result(String outno) throws Exception {
        //1. 查询数据库
        QueryWrapper<PayLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pay_id",outno);
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        PayLog payLog = payLogMapper.selectOne(queryWrapper);
        //2.微信服务器查询
        if( payLog == null ) {
            //1.执行微信查询
            Random random = new Random(100);
            int seq = random.nextInt();
            //2.插入本地log
            payLog = new PayLog(seq+outno,1,"weixin query",outno,new Date());;
            payLogMapper.insert(payLog);
        }
        return null;
    }
}
