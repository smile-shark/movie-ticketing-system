package com.movie.filter;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.movie.common.resp.RespCode;
import com.movie.exception.BusinessException;
import com.movie.filter.request.RequestWrapper;
import com.movie.utils.AesUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;


@WebFilter(urlPatterns = "/customer/*")
public class CustomerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        // 跳过验证码接口
        if(requestURI.endsWith("/customer/email/verification/code")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 包装请求以便多次读取body
        RequestWrapper wrappedRequest = new RequestWrapper(httpRequest);

        // 处理注册/登录
        if(("POST".equalsIgnoreCase(httpRequest.getMethod()) &&
                (requestURI.endsWith("/customer/register") ||
                        requestURI.endsWith("/customer/login")))) {
            try {
                // 1. 获取原始请求体
                String requestBody = wrappedRequest.getBody();
                System.out.println("Raw request: " + requestBody);

                // 2. 解析JSON
                JSONObject json = JSON.parseObject(requestBody);
                String encryptedPassword = json.getString("password");
                String userPassword = json.getString("userPassword");

                if(encryptedPassword != null && !encryptedPassword.isEmpty()) {
                    // 3. 解密密码
                    String decryptedPassword = AesUtils.decrypt(encryptedPassword);
                    System.out.println("Decrypted password: " + decryptedPassword);

                    // 4. 替换密码字段
                    json.put("password", decryptedPassword);

                    // 5. 更新请求体
                    wrappedRequest.resetBody(json.toJSONString());
                }else
                if(userPassword != null && !userPassword.isEmpty()) {
                    // 3. 解密密码
                        String decryptedUserPassword = AesUtils.decrypt(userPassword);
                    System.out.println("Decrypted password: " + decryptedUserPassword);

                    // 4. 替换密码字段
                    json.put("userPassword", decryptedUserPassword);

                    // 5. 更新请求体
                    wrappedRequest.resetBody(json.toJSONString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(RespCode.DECRYPT_ERROR);
            }
        }
        filterChain.doFilter(wrappedRequest, servletResponse);
    }
}