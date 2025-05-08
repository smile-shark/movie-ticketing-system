//package com.movie.provide;
//
//import com.alibaba.fastjson2.JSONWriter;
//import com.alibaba.fastjson2.writer.ObjectWriter;
//import java.lang.reflect.Type;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//public class LocalTimeWriter implements ObjectWriter<LocalTime> {
//    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
//
//    @Override
//    public void write(JSONWriter writer,
//                      Object object,
//                      Object fieldName,
//                      Type fieldType,
//                      long features) {
//        LocalTime time = (LocalTime) object;
//        writer.writeString(time.format(FORMATTER));
//    }
//}