package com.movie.entity;

import com.movie.entity.groups.CinemaManagementLogin;
import com.movie.entity.groups.CustomerLogin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class CinemaManagement {
    private String cinemaManagementId;
    @NotBlank(message = "邮箱不能为空",groups = {CinemaManagementLogin.class})
    @Email(message = "邮箱格式不正确",groups = {CinemaManagementLogin.class})
    private String cinemaManagementEmail;
    @NotBlank(message = "密码不能为空",groups = {CinemaManagementLogin.class})
    @Length(min=6,max = 20,message = "密码长度必须在6到20位之间",groups = {CinemaManagementLogin.class})
    private String cinemaManagementPassword;
    private String cinemaManagementProfilePicture;
    private String token;
}
