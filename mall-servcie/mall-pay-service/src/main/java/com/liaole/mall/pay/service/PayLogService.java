package com.liaole.mall.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liaole.mall.pay.model.PayLog;

public interface PayLogService extends IService<PayLog> {
    void log(PayLog payLog);
}
