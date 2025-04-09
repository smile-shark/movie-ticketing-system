package com.movie.entity;

import com.movie.entity.groups.CustomerLogin;
import com.movie.entity.groups.SendEmail;
import com.movie.entity.groups.VerifyEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    @NotBlank(message = "ID不能为空")
    private String userId;
    private String countyId;
    @NotBlank(message = "邮箱不能为空",groups = {CustomerLogin.class})
    @Email(message = "邮箱格式不正确",groups = {CustomerLogin.class})
    private String userEmail;
    @NotBlank(message = "密码不能为空",groups = {CustomerLogin.class})
    @Length(min=6,max = 20,message = "密码长度必须在6到20位之间",groups = {CustomerLogin.class})
    private String userPassword;
    private String userName;
    private String userProfilePicture;
    private Integer userPoints;
    private Integer userLevel;
    private Integer userState;
}
