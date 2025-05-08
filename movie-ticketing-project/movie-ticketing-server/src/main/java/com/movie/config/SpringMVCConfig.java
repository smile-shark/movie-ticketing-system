package com.movie.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.movie.filter.CinemaManagementFilter;
import com.movie.filter.CustomerFilter;
import com.movie.filter.GlobalFilter;
import com.movie.filter.PlatformManagementFilter;
import com.movie.provide.LocalTimeDeserializer;
import com.movie.provide.LocalTimeSerializer;
import com.movie.utils.AesUtils;
import com.movie.utils.TokenUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.movie.controller", "com.movie.utils", "com.movie.config"})
@EnableWebMvc
public class SpringMVCConfig implements WebApplicationInitializer, WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建FastJson配置
        FastJsonConfig config = new FastJsonConfig();

        // 禁用循环引用检测（关闭$ref）
        config.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect
        );

        /*// 注册自定义序列化器
        ObjectWriterProvider writerProvider = JSONFactory.getDefaultObjectWriterProvider();
        writerProvider.register(LocalTime.class, new LocalTimeWriter());

        // 注册自定义反序列化器
        ObjectReaderProvider readerProvider = JSONFactory.getDefaultObjectReaderProvider();
        readerProvider.register(LocalTime.class, new LocalTimeReader())*/
        ;
        // 注册自定义序列化器（转为时间戳）
        SerializeConfig.getGlobalInstance().put(LocalDateTime.class, new ObjectSerializer() {
            @Override
            public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
                LocalDateTime time = (LocalDateTime) object;
                long timestamp = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                serializer.write(timestamp);
            }
        });

        SerializeConfig.getGlobalInstance().put(LocalTime.class, new LocalTimeSerializer());

        // 注册自定义反序列化器 (FastJSON 1.x)
        ParserConfig.getGlobalInstance().putDeserializer(LocalTime.class, new LocalTimeDeserializer());

        // 配置HttpMessageConverter
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 添加到转换器列表首位
        converters.addFirst(converter);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 获取 Spring 应用上下文
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringMVCConfig.class);

        // 注册 DelegatingFilterProxy
        DelegatingFilterProxy customerFilter = new DelegatingFilterProxy("customerFilter");
        servletContext.addFilter("customerFilter", customerFilter)
                .addMappingForUrlPatterns(null, false, "/customer/*");
        DelegatingFilterProxy cinemaManagementFilter = new DelegatingFilterProxy("cinemaManagementFilter");
        servletContext.addFilter("cinemaManagementFilter", cinemaManagementFilter)
                .addMappingForUrlPatterns(null, false, "/cinema/management/*");
        DelegatingFilterProxy platformManagementFilter = new DelegatingFilterProxy("platformManagementFilter");
        servletContext.addFilter("platformManagementFilter", platformManagementFilter)
                .addMappingForUrlPatterns(null, false, "/platform/*");

    }

    @Bean
    public Filter customerFilter(AesUtils aesUtils, TokenUtils tokenUtils) {
        return new CustomerFilter(aesUtils, tokenUtils);
    }

    @Bean
    public Filter cinemaManagementFilter(AesUtils aesUtils, TokenUtils tokenUtils) {
        return new CinemaManagementFilter(aesUtils, tokenUtils);
    }

    @Bean
    public Filter platformManagementFilter(AesUtils aesUtils, TokenUtils tokenUtils) {
        return new PlatformManagementFilter(aesUtils, tokenUtils);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();//文件解析对象
    }
}
