package com.movie.exception;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.util.List;

@RestControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result testException(Exception e){
        e.printStackTrace();
        return Result.error(RespCode.ERROR);
    }
    @ExceptionHandler(BusinessException.class)
    public Result testBusinessException(BusinessException e){
        return Result.error(e.getRespCode());
    }
    @ExceptionHandler(MessagingException.class)
    public Result testMessagingException(MessagingException e){
        e.printStackTrace();
        return Result.error(RespCode.SEND_EMAIL_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public Result methodArgumentValidException(BindException e){
        BindingResult bindingResult = e.getBindingResult();//获取注解上的异常信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            /* TODO stringBuilder.append(fieldError.getField());
            stringBuilder.append("===>");*/
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(";\n");
        }
        return Result.error().setMessage(stringBuilder.toString());
    }

}
