package com.liaole.mall.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;

public class AESUtil {

    /**
     *
     * @param buffer  明文/密文
     * @param appsecret  秘钥,16位
     * @param mode   处理方式 1 加密 2 解密
     * @return
     * @throws Exception
     */
    public static byte[] encryptAndDecrypt(byte[] buffer,String appsecret,Integer mode ) throws Exception {
        //1 加载加密处理对象，该对象会提供加密算法，秘钥生成，秘钥转换，秘钥管理等
        Security.addProvider(new BouncyCastleProvider());
        //2 创建秘钥对象，并指定算法
        SecretKeySpec secretKey = new SecretKeySpec(appsecret.getBytes("UTF-8"),"AES");
        //3 设置Cipher的加密模式，AES/ECB/PKCS7Padding BC指定算法对象
        Cipher cipher =Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
        //4 初始化加密配置
        cipher.init(mode,secretKey);
        //5 执行加密和解密
        return  cipher.doFinal(buffer);
    }

    public static void main(String[] args) throws Exception {
        //明文
        String content = "SpringCloud Alibaba!";
        //16位秘钥
        String appsecret="aaaaaaaaaaaaaaaa";
        String appsecret1 = MD5.md5(appsecret);
        //加密
        byte[] encrypt = encryptAndDecrypt(content.getBytes("UTF-8"), appsecret, 1);
        System.out.println("加密后的密文："+Base64Util.encode(encrypt));

        //解密
        byte[] decrypt = encryptAndDecrypt(encrypt, appsecret, 2);
        System.out.println(new String(decrypt,"UTF-8"));

        //加密
        byte[] encrypt1 = encryptAndDecrypt(content.getBytes("UTF-8"), appsecret1, 1);
        System.out.println("加密后的密文："+Base64Util.encode(encrypt1));

        //解密
        byte[] decrypt1 = encryptAndDecrypt(encrypt1, appsecret1, 2);
        System.out.println(new String(decrypt1,"UTF-8"));
    }

}
