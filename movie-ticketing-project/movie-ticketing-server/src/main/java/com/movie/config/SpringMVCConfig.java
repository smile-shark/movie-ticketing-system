package com.movie.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.movie.filter.CinemaManagementFilter;
import com.movie.filter.CustomerFilter;
import com.movie.filter.GlobalFilter;
import com.movie.filter.PlatformManagementFilter;
import com.movie.intercopter.CustomerJwtInterceptor;
import com.movie.utils.AesUtils;
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

import java.util.Collections;
import java.util.List;
@Configuration
@ComponentScan(basePackages = {"com.movie.controller","com.movie.utils","com.movie.config"})
@EnableWebMvc
public class SpringMVCConfig implements WebApplicationInitializer, WebMvcConfigurer {

    @Autowired
    private CustomerJwtInterceptor customerJwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customerJwtInterceptor)
                .addPathPatterns("/customer/*")
                .excludePathPatterns("/customer/login","/customer/register","/customer/email/verification/code");
    }

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
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        fastJsonHttpMessageConverter.setSupportedMediaTypes(
                Collections.singletonList(MediaType.APPLICATION_JSON)
        );

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        converters.addFirst(fastJsonHttpMessageConverter);
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
    public Filter customerFilter(AesUtils aesUtils) {
        return new CustomerFilter(aesUtils);
    }
    @Bean
    public Filter cinemaManagementFilter(AesUtils aesUtils){
        return new CinemaManagementFilter(aesUtils);
    }
    @Bean
    public Filter platformManagementFilter(AesUtils aesUtils) {
        return new PlatformManagementFilter(aesUtils);
    }
    @Bean
    public MultipartResolver multipartResolver(){
        return new StandardServletMultipartResolver();//文件解析对象
    }
}
