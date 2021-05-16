package com.liaole.mall.seckill.controller;

import com.liaole.mall.seckill.mq.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "/kafka")
public class TestKafka {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping(value = "send/{name}")
    public void kafkaTest(@PathVariable(value="name")String name){
        Object message = "hello "+ name +"this is kafka test"+new Random().nextInt();
        kafkaProducer.send(message);
    }
}
