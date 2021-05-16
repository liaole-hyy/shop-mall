package com.liaole.mall.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * URL工具
 */
public class UrlUtils {

    /**
     * 去掉URL中指定的参数
     */
    public static String replateUrlParameter(String url,String... names){
        if( names == null){
            return url;
        }
        for (String name : names) {
            url = url.replaceAll("(&"+name+"=([0-9\\w]+))|("+name+"=([0-9\\w]+)&)|("+name+"=([0-9\\w]+))", "");
        }
        return url;
    }

    /***
     * 当前请求地址组装,并去掉指定参数
     */
    public static String map2url(String baseUrl, Map<String,Object> searchMap, String... names){
        //参数获取
        String parm = map2parm(searchMap);
        if(!StringUtils.isEmpty(parm)){
            baseUrl+="?"+parm;
        }
        //去掉指定参数
        baseUrl = replateUrlParameter(baseUrl,names);
        return baseUrl;
    }

    /**
     *   将map对象转换成url参数
     * @param map
     * @return
     */
    public static String map2parm(Map<String,Object> map){
        if( map == null){
            return  "" ;
        }

        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            sb.append(entry.getKey()+"="+entry.getValue());
            sb.append("&");
        }
        String parameters = sb.toString();
        //去掉末尾&
        if (parameters.endsWith("&")) {
            parameters = StringUtils.substringBeforeLast(parameters ,"&");
        }
        return parameters;
    }

    public static void main(String[] args) {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("body", "商城订单-");
        dataMap.put("out_trade_no","AAA");
        dataMap.put("device_info", "PC");
        dataMap.put("fee_type", "CNY");
        dataMap.put("total_fee", "1");

        String url = map2url("192.168.5.1:8090/wx",dataMap,null);
        System.out.println(url);
        String url1 = map2url("192.168.5.1:8090/wx",dataMap,new String[]{"total_fee"});
        System.out.println(url1);
    }
}
