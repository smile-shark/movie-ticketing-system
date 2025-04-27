package com.movie.provide;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeReader implements ObjectReader<LocalTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime readObject(JSONReader reader,
                                Type fieldType,
                                Object fieldName,
                                long features) {
        String timeStr = reader.readString();
        if ("24:00".equals(timeStr)) {
            return LocalTime.MAX; // 处理24:00特殊情况
        }
        return LocalTime.parse(timeStr, FORMATTER);
    }
}