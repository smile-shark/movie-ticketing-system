package com.movie.provide;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer implements ObjectSerializer {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void write(JSONSerializer serializer,
                      Object object,
                      Object fieldName,
                      Type fieldType,
                      int features) throws IOException {
        LocalTime time = (LocalTime) object;
        if (time != null) {
            // 处理 24:00 特殊情况
            String timeStr = time.equals(LocalTime.MAX) ? "24:00" : time.format(FORMATTER);
            serializer.write(timeStr);
        } else {
            serializer.out.writeNull();
        }
    }
}