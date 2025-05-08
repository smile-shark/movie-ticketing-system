package com.movie.provide;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeDeserializer implements ObjectDeserializer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public LocalTime deserialze(DefaultJSONParser parser,
                                Type type,
                                Object fieldName) {
        String timeStr = parser.parseObject(String.class);
        if (timeStr == null) {
            return null;
        }
        // 处理 24:00 特殊情况
        return "24:00".equals(timeStr) ? LocalTime.MAX : LocalTime.parse(timeStr, FORMATTER);
    }

    @Override
    public int getFastMatchToken() {
        return 0; // 默认值
    }
}