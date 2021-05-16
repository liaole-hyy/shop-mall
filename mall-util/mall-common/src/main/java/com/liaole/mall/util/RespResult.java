package com.liaole.mall.util;

import java.io.Serializable;

/**
 *  响应信息的封装体对象
 */
public class RespResult<T> implements Serializable {

    //响应信息的结果
    private T data;

    /**
     *  状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    public RespResult() {
    }

    public RespResult(RespCode resultCode){
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public  RespResult(T data,RespCode resultCode){
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public static RespResult ok(){
        return new RespResult(null,RespCode.SUCCESS);
    }

    public static RespResult ok(Object data){
        return new RespResult(data,RespCode.SUCCESS);
    }

    public static RespResult error(){
        return new RespResult(null,RespCode.ERROR);
    }

    //
    public static RespResult error(String message){
        return secByError(RespCode.ERROR.getCode(),message);
    }

    //自定义异常
    private static RespResult secByError(Integer code,String message) {
        RespResult err = new RespResult();
        err.setCode(code);
        err.setMessage(message);
        return  err;
    }
    //这个方法有歧义 如果RespCode 这个构建不是错误类的 就有问题
    public static RespResult error(RespCode respCode) {
        return new RespResult(respCode);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
