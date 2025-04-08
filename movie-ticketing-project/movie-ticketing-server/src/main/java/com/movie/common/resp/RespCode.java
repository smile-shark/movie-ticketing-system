package com.movie.common.resp;

public enum RespCode {
    SUCCESS(200, "成功"),
    SEND_EMAIL_SUCCESS(200, "验证码发送成功"),
    ERROR(500, "失败"),
    DATABASE_ERROR(500, "数据库连接失败"),
    PARAM_ERROR(5001, "参数错误"),
    SEND_EMAIL_ERROR(5002, "验证码发送失败"),
    USER_ALREADY_EXISTS(5003, "用户已存在"),
    ;

    private final int code;
    private final String message;

    RespCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
