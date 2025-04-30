package com.movie.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.movie.common.resp.RespCode;
import com.movie.exception.BusinessException;
import com.movie.filter.request.RequestWrapper;
import com.movie.utils.AesUtils;
import com.movie.utils.SendErrorResponseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class CinemaManagementFilter implements Filter {
    private final AesUtils aesUtils;
    public CinemaManagementFilter(AesUtils aesUtils) {
        this.aesUtils = aesUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String requestURI = httpRequest.getRequestURI();

        // 包装请求以便多次读取body
        RequestWrapper wrappedRequest = new RequestWrapper(httpRequest);

        // 处理注册/登录
        if(("POST".equalsIgnoreCase(httpRequest.getMethod()) &&
                (requestURI.endsWith("/cinema/management/register") ||
                        requestURI.endsWith("/cinema/management/login")))) {
            try {
                // 1. 获取原始请求体
                String requestBody = wrappedRequest.getBody();
                System.out.println("Raw request: " + requestBody);

                // 2. 解析JSON
                JSONObject json = JSON.parseObject(requestBody);
                String encryptedPassword = json.getString("password");
                String cinemaManagementPassword = json.getString("cinemaManagementPassword");

                if(encryptedPassword != null && !encryptedPassword.isEmpty()) {
                    // 3. 解密密码
                    String decryptedPassword = aesUtils.decrypt(encryptedPassword);
                    System.out.println("Decrypted password: " + decryptedPassword);

                    // 4. 替换密码字段
                    json.put("password", decryptedPassword);

                    // 5. 更新请求体
                    wrappedRequest.resetBody(json.toJSONString());
                }else
                if(cinemaManagementPassword != null && !cinemaManagementPassword.isEmpty()) {
                    // 3. 解密密码
                        String decryptedPassword = aesUtils.decrypt(cinemaManagementPassword);
                    System.out.println("Decrypted password: " + decryptedPassword);

                    // 4. 替换密码字段
                    json.put("cinemaManagementPassword", decryptedPassword);

                    // 5. 更新请求体
                    wrappedRequest.resetBody(json.toJSONString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                SendErrorResponseUtil.sendErrorResponse(httpResponse,RespCode.DECRYPT_ERROR);
                return;
            }
        }
        filterChain.doFilter(wrappedRequest, servletResponse);
    }
}