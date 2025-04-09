package com.movie.entity;

import com.movie.entity.groups.SendEmail;
import com.movie.entity.groups.VerifyEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class EmailVerify{
    @NotBlank(message = "邮箱不能为空",groups = {SendEmail.class, VerifyEmail.class})
    @Email(message = "邮箱格式不正确",groups = {SendEmail.class, VerifyEmail.class})
    private String email;
    @NotBlank(message = "邮箱验证码不能为空",groups = {VerifyEmail.class})
    @Length(min = 4, max = 4, message = "邮箱验证码长度必须为4位",groups = {VerifyEmail.class})
    private String emailVerifyCode;
    @NotBlank(message = "密码不能为空",groups = {VerifyEmail.class})
    @Length(min=6,max = 20,message = "密码长度必须在6到20位之间",groups = {VerifyEmail.class})
    private String password;
    private LocalDateTime endTime;
}
