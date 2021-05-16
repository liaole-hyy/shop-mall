package com.liaole.mall.pay.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.WXPayXmlUtil;
import com.liaole.mall.pay.model.PayLog;
import com.liaole.mall.pay.service.PayLogService;
import com.liaole.mall.pay.service.WeixinPayService;
import com.liaole.mall.util.RespResult;
import com.liaole.mall.util.Signature;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/wx")
public class WeixinPayController {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private Signature signature;

    @Autowired
    private WeixinPayService weixinPayService;

    /**
     *  记录支付结果
     *  执行事务消息发送
     */
    @GetMapping(value = "/result")
    public RespResult payLog(){
        //记录日志
        PayLog payLog = new PayLog("1",1,"test","No001",new Date());
        Message<String> message =  MessageBuilder.withPayload(JSON.toJSONString(payLog)).build();

        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("rocket","log",message,null);
        if( RocketMQLocalTransactionState.COMMIT.equals(transactionSendResult.getLocalTransactionState())){
            System.out.println("回复微信确认收到消息");
        }
        return RespResult.ok();
    }


    /**
     * 支付状态查询
     * @param outno
     * @return
     */
    @GetMapping(value = "/result/{outno}")
    public RespResult<PayLog> query(@PathVariable(value = "outno")String outno) throws Exception {
        PayLog payLog = weixinPayService.result(outno);
        return  RespResult.ok();
    }

    /**
     *  进行预支付 下单
     * @param ciphertext
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/pay")
    public RespResult<Map> pay(@RequestParam(value = "ciptext")String ciphertext) throws Exception {
        //数据解析，并验签校验
        Map<String,String> map = signature.security(ciphertext);
        if( map != null ){
            //Map<String,String> resultMap = new HashMap<>();
            weixinPayService.preOrder(map);

            return RespResult.ok(map);
        }

        return RespResult.error("支付系统繁忙，请稍后再试");
    }


    /**
     * 退款通知结果
     * @param request
     * @return
     */
    @RequestMapping("/refund/result")
    public String refundResult(HttpServletRequest request) throws Exception{
        System.out.println("**************退款通知***********");
        //获取结果
        ServletInputStream servletInputStream = request.getInputStream();
        //接受存储网络输入流
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        //缓冲区定义
        byte[] buffer = new byte[1024];
        int len = 0;
        //循环读取输入流,写入到os
        while ((len = servletInputStream.read(buffer)) != -1 ){
            os.write(buffer,0,len);
        }
        //关闭资源
        os.close();
        servletInputStream.close();

        //将字节数组结果转换xml 字符串
        String xmlResult = new String(os.toByteArray(),"UTF-8");
        //xml转换map
        Map<String,String> responseMap = WXPayUtil.xmlToMap(xmlResult);

        //发送mq消息,普通消息，非事务消息
        Message message =MessageBuilder.withPayload(JSON.toJSONString(responseMap)).build();
        rocketMQTemplate.send("lastrefundresult",message);

        //返回结果
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("return_code","SUCCESS");
        resultMap.put("return_msg","OK");
        //返回给微信
        return WXPayUtil.mapToXml(resultMap);

    }
}
