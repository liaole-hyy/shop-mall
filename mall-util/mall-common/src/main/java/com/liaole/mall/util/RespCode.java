package com.liaole.mall.util;

/**
 *  状态码枚举类
 */
public enum  RespCode {

    SUCCESS(20000,"操作成功"),

    ERROR(50000,"操作失败"),

    SYSTEM_ERROR(50001,"系统错误");

    private Integer code ;

    private String message ;

    RespCode() {
    }

    RespCode(Integer code, String message) {
        this.code = code;
        this.message = message;
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
