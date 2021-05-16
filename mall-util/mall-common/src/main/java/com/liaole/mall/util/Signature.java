package com.liaole.mall.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *  支付或者接口安全 数据验证，加密解密
 */
public class Signature {

    //秘钥
    private String skey ;

    //验签加盐值
    private String salt;

    public Signature(String skey, String salt) {
        this.skey = skey;
        this.salt = salt;
    }

    /**
     *  密文解密，并将Map数据验签后，返回有效的map数据
     * @param ciphertext  密文
     * @return
     * @throws Exception
     */
    public Map<String,String> security(String ciphertext) throws Exception {
        // 1.解密
        String decrypt =new String(AESUtil.encryptAndDecrypt(Base64Util.decodeURL(ciphertext),skey,2),"UTF-8");
        //2.将明文转换成Map,并根据key降序
        Map<String,String> decryptTreeMap = JSON.parseObject(decrypt, TreeMap.class);
        //3.验签
        String signature = decryptTreeMap.remove("signature");
        //4.本地计算后的签名
        String localSignature = MD5.md5(JSON.toJSONString(decryptTreeMap),salt);
        //true 验签成功，false  验签失败
        return signature.equals(localSignature)? decryptTreeMap : null;
    }

    /**
     *  Map加密，并携带验签，
     * @param dataMap
     * @return  返回具有携带验签加密后base64数据
     * @throws Exception
     */
    public String security(Map<String,String> dataMap) throws Exception {
        //1.将dataMap转成TreeMap
        dataMap = JSON.parseObject(JSON.toJSONString(dataMap),TreeMap.class);
        //2.将TreeMap转换成json
        String treeJson = JSON.toJSONString(dataMap);
        //3.执行MD5摘要加密
        String signature = MD5.md5(treeJson,salt);
        //4.摘要加密内容添加到dataMap中
        dataMap.put("signature",signature);
        //5.对dataMap进行AES加密并base64后的数据
        return  Base64Util.encodeURL(AESUtil.encryptAndDecrypt(JSON.toJSONString(dataMap).getBytes("UTF-8"),skey,1));
    }

    public static void main(String[] args) throws Exception {
        String skey="ab2cc473d3334c39";
        String salt="XPYQZb1kMES8HNaJWW8+TDu/4JdBK4owsU9eXCXZDOI=";

        // 需要加密的字串
        Map<String,String> dataMap = new HashMap<String,String>();
        dataMap.put("body", "商城订单-");
        dataMap.put("out_trade_no","AAA");
        dataMap.put("device_info", "PC");
        dataMap.put("fee_type", "CNY");
        dataMap.put("total_fee", "1");
        dataMap.put("spbill_create_ip","192.168.100.130");
        dataMap.put("notify_url", "http://www.example.com/wxpay/notify");
        dataMap.put("trade_type", "NATIVE");

        Signature signature =new Signature(skey,salt);
        String cSrc = signature.security(dataMap);
        System.out.println("加密后的数据如下:");
        System.out.println(cSrc);


        System.out.println("解密:"+cSrc);
        Map<String,String> result = signature.security(cSrc);
        System.out.println(result);

    }
}
