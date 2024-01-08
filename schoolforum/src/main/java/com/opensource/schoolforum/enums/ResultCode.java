package com.opensource.schoolforum.enums;

import org.apache.commons.lang3.ObjectUtils;

public enum ResultCode {

    SUCCESS(200,"success"),
    FAILURE(500,"fail"),
    PARAMETERERROR(400,"parameter error"),
    USERNAMEPASSWORDERROR(500,"Incorrect username or password"),
    ERROR(10000,"Unknown reason error!"),
    SERVICE_ERROR(50000,"Server Exception"),
    UNLOGIN(401,"Please log in again");

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static ResultCode of(String message) {
        if (ObjectUtils.isEmpty(message)) {
            throw new RuntimeException("参数错误");
        }
        for (ResultCode resultCode : values()) {
            if (resultCode.message.equals(message)) {
                return resultCode;
            }
        }
        return ERROR;
    }
}
