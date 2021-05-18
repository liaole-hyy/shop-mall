package com.liaole.mall.api.gateway.permission;

import com.liaole.mall.util.JwtToken;
import com.liaole.mall.util.MD5;

import java.util.Map;
import java.util.Objects;

/**
 *  全局身份校验
 */
public class AuthorizationInterceptor {

    public static Map<String,Object> jwtVerify(String token,String clientIp){
        //解析token
        Map<String,Object> dataMap = JwtToken.parseToken(token);
        //获取token中ipMd5
        String md5Ip = dataMap.get("ip").toString();
        //比较Token中ip的md5值和用户ipmd5值
        try {
            if( MD5.md5(clientIp).equals( md5Ip)){
                return  dataMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
