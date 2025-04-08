package com.movie.exception;

import com.movie.common.resp.RespCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{
    private RespCode respCode;

    public BusinessException(RespCode respCode){
        this.respCode = respCode;
    }
}