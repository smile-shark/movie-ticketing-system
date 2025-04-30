package com.movie.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.movie.common.resp.RespCode;
import com.movie.exception.BusinessException;
import com.movie.filter.request.RequestWrapper;
import com.movie.utils.AesUtils;
import com.movie.utils.SendErrorResponseUtil;
import com.movie.utils.TokenUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;


//@WebFilter(urlPatterns = "/customer/*")
public class CustomerFilter implements Filter {
    private final AesUtils aesUtils;
    private final TokenUtils tokenUtils;
    public CustomerFilter(AesUtils aesUtils,TokenUtils tokenUtils) {
        this.aesUtils = aesUtils;
        this.tokenUtils = tokenUtils;
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
                    String decryptedPassword = aesUtils.decrypt(encryptedPassword);
                    System.out.println("Decrypted password: " + decryptedPassword);

                    // 4. 替换密码字段
                    json.put("password", decryptedPassword);

                    // 5. 更新请求体
                    wrappedRequest.resetBody(json.toJSONString());
                }else
                if(userPassword != null && !userPassword.isEmpty()) {
                    // 3. 解密密码
                        String decryptedUserPassword = aesUtils.decrypt(userPassword);
                    System.out.println("Decrypted password: " + decryptedUserPassword);

                    // 4. 替换密码字段
                    json.put("userPassword", decryptedUserPassword);

                    // 5. 更新请求体
                    wrappedRequest.resetBody(json.toJSONString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                SendErrorResponseUtil.sendErrorResponse(httpResponse,RespCode.DECRYPT_ERROR);
                return;
            }
        }
        else if(!requestURI.endsWith("/customer/email/verification/code")) {
            String token = httpRequest.getHeader("Authorization");
            if(token==null || token.isEmpty()){
                SendErrorResponseUtil.sendErrorResponse(httpResponse,RespCode.TOKEN_VERIFY_ERROR);
                return;
            }
            try{
                if(!tokenUtils.verifyToken(token)){
                    SendErrorResponseUtil.sendErrorResponse(httpResponse,RespCode.TOKEN_VERIFY_ERROR);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
                SendErrorResponseUtil.sendErrorResponse(httpResponse,RespCode.TOKEN_VERIFY_ERROR);
                return;
            }
            System.out.println("验证token成功");
        }
        filterChain.doFilter(wrappedRequest, servletResponse);
    }
}