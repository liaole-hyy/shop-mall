package com.liaole.mall.pay.config;

import com.liaole.mall.util.Signature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    //秘钥
    @Value("${payconfig.aes.skey}")
    private  String skey;

    @Value("${payconfig.aes.salt}")
    private String salt;

    /**
     * 验签对象
     * @return
     */
    @Bean(value = "signature")
    public Signature signature(){
        return  new Signature(skey,salt);
    }
}
