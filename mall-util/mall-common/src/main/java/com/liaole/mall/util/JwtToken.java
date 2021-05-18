package com.liaole.mall.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *  jwt token创建和解析
 */
public class JwtToken {

    //默认秘钥
    private static final String DEFAULT_SECRET="springcloudalibaba";

    /**
     *  创建Jwt令牌
     *  秘钥:secret
     *  载荷:dataMap(map)
     */
    public  static String createToken(Map<String,Object> dataMap, String secret){
        //确认秘钥
        if(StringUtils.isEmpty(secret)){
            secret = DEFAULT_SECRET;
        }
        //确认签名算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //jwt令牌创建
        return JWT.create()
                .withClaim("body",dataMap)  //载体
                .withIssuer("LiaoLe")  //签发者
                .withSubject("JWT令牌") //主题
                .withAudience("member") //接收方
                .withExpiresAt(new Date(System.currentTimeMillis()+3600000)) //过期时间
                .withNotBefore(new Date(System.currentTimeMillis()+1000)) //1秒后使用
                .withIssuedAt(new Date()) //签发时间
                .withJWTId(UUID.randomUUID().toString().replace("-","")) //唯一签发标识
                .sign(algorithm);
    }

    /**
     * 创建Jwt令牌
     * @param dataMap
     * @return
     */
    public  static String createToken(Map<String,Object> dataMap){
        return createToken(dataMap,null);
    }

    /**
     *  令牌解析
     */
    public static Map<String,Object> parseToken(String token,String secret){
        //确认秘钥
        if(StringUtils.isEmpty(secret)){
            secret = DEFAULT_SECRET;
        }
        //确认签名算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //创建令牌校验对象
        JWTVerifier verifier = JWT.require(algorithm).build();
        //校验解析
        DecodedJWT jwt = verifier.verify(token);

        return  jwt.getClaim("body").asMap();
    }

    /**
     *  令牌解析(默认)
     */
    public static Map<String,Object> parseToken(String token){
        return parseToken(token,null);
    }

    public static void main(String[] args) throws InterruptedException {
        //创建令牌
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("name","zhangsan");
        dataMap.put("address","武汉");

        //创建令牌
        String token =createToken(dataMap);
        System.out.println(token);

        //休眠一秒钟
        TimeUnit.SECONDS.sleep(1);

        System.out.println(parseToken(token+"s"));
    }
}
