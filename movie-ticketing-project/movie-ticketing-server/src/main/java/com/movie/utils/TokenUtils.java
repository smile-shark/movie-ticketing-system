package com.movie.utils;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.movie.common.resp.RespCode;
import com.movie.exception.BusinessException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
@PropertySource("classpath:KEY.properties")
public class TokenUtils {
    @Value("${JWT_KEY}")
    private String JWT_KEY;
    @Value("${JWT_EXPIRATION_TIME}")
    private Long JWT_EXPIRATION_TIME;

    public <T> String createToken(T o) {
        Class<?> aClass = o.getClass();
        Map<String, Object> map = new HashMap<>();
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        map.put("expire_time", System.currentTimeMillis());
        return JWT.create().addPayloads(map)
                .setIssuedAt(DateUtil.date())// 设置签发时间
                .setNotBefore(DateUtil.date())// 生效时间
                .setExpiresAt(DateUtil.date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))// 设置过期时间
                .setKey(JWT_KEY.getBytes())
                .sign();
    }

    public <T> T parseToken(String token, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        JWT jwt = JWTUtil.parseToken(token);
        T t = tClass.newInstance();
        for (Field field : tClass.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = jwt.getPayload(field.getName());
            if (value instanceof NumberWithFormat) {
                value = ((NumberWithFormat) value).intValue();
            }
            field.set(t, value);
        }
        return t;
    }

    public boolean verifyToken(String token) {
        JWTValidator.of(token).validateDate(DateUtil.date());
        return JWTUtil.verify(token, JWT_KEY.getBytes());
    }
}
