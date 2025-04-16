package com.movie.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.movie.common.resp.RespCode;
import com.movie.exception.BusinessException;
import com.movie.filter.request.RequestWrapper;
import com.movie.utils.AesUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class PlatformManagementFilter implements Filter {
    private final AesUtils aesUtils;
    public PlatformManagementFilter(AesUtils aesUtils) {
        this.aesUtils = aesUtils;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        // 包装请求以便多次读取body
        RequestWrapper wrappedRequest = new RequestWrapper(httpRequest);
        // 处理注册/登录
        if(("POST".equalsIgnoreCase(httpRequest.getMethod()) &&
                        requestURI.endsWith("/platform/login"))) {
            try {
                // 1. 获取原始请求体
                String requestBody = wrappedRequest.getBody();
                System.out.println("Raw request: " + requestBody);

                // 2. 解析JSON
                JSONObject json = JSON.parseObject(requestBody);
                String userPassword = json.getString("platformManagementPassword");

                if(userPassword != null && !userPassword.isEmpty()) {
                    // 3. 解密密码
                    String decryptedUserPassword = aesUtils.decrypt(userPassword);
                    System.out.println("Decrypted password: " + decryptedUserPassword);

                    // 4. 替换密码字段
                    json.put("platformManagementPassword", decryptedUserPassword);

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
