package com.movie.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
@PropertySource("classpath:email-config.properties")
public class EmailUtils {
    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.smtp.port}")
    private String port;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;

    public boolean sendVerificationCode(String email,String verifyCode){
        try{
            // 连接到SMTP服务器587端口
            Properties properties = new Properties();
            properties.put("mail.smtp.host",host);// SMTP主机名
            properties.put("mail.smtp.port",port);// 主机端口号
            properties.put("mail.smtp.starttls.enable",starttls); // 启用TLS加密
            properties.put("mail.smtp.auth",auth);// 启动身份验证
            // 获取Session实例：
            Session session=Session.getInstance(
                    properties,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username,password);
                        }
                    }
            );
            // 发送邮件
            MimeMessage message=new MimeMessage(session);
            // 设置发送方地址：
            message.setFrom(new InternetAddress(username));
            // 设置接收方地址：
            message.setRecipient(
                    MimeMessage.RecipientType.TO,
                    new InternetAddress(email)
            );
            // 设置邮件主题：
            message.setSubject("验证码","UTF-8");
            message.setText("<html><body>"
                    + "<h1>看吧电影：</h1>"
                    + "<p>您的验证码是: " + verifyCode + "</p>"
                    + "<p>有效时间为5分钟，请尽快输入。</p>"
                    + "</body></html>","UTF-8","html");
            // 发送：
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
