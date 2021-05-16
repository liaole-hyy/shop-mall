package com.liaole.mall.seckill.mq;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liaole.mall.util.OffsetJsonDeserializer;
import com.liaole.mall.util.OffsetJsonSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  自定义kafka消息对象
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class Message implements Serializable {

    //消息唯一id
    protected String msgId;

    @JsonSerialize(using = OffsetJsonSerializer.class)
    @JsonDeserialize(using = OffsetJsonDeserializer.class)
    //创建时间
    private LocalDateTime createAt ;

    //请求信息
    protected Object reqInfo;

    protected String paramDTO;


}
