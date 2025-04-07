package com.movie.common.resp;

public enum RespCode {
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    PARAM_ERROR(5001, "参数错误"),
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
