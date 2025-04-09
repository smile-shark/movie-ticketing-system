package com.movie.common.resp;

import com.movie.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;
@Data
@Component
public class Result {
    private boolean success;
    private Integer code;
    private String message;
    private Object data;

    public static  Result success() {
        return new Result()
                .setSuccess(true)
                .setCode(RespCode.SUCCESS.getCode())
                .setMessage(RespCode.SUCCESS.getMessage());
    }
    public static  Result success(Object data) {
        return new Result()
                .setSuccess(true)
                .setCode(RespCode.SUCCESS.getCode())
                .setMessage(RespCode.SUCCESS.getMessage())
                .setData(data);
    }
    public static  Result success(RespCode code) {
        return new Result()
                .setSuccess(true)
                .setCode(code.getCode())
                .setMessage(code.getMessage());
    }

    public static Result success(RespCode code, User have) {
        return new Result()
                .setSuccess(true)
                .setCode(code.getCode())
                .setMessage(code.getMessage())
                .setData(have);
    }
    public static  Result error() {
        return new Result()
                .setSuccess(false)
                .setCode(RespCode.ERROR.getCode())
                .setMessage(RespCode.ERROR.getMessage());
    }
    public static  Result error(RespCode code) {
        return new Result()
                .setSuccess(false)
                .setCode(code.getCode())
                .setMessage(code.getMessage());
    }

    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
