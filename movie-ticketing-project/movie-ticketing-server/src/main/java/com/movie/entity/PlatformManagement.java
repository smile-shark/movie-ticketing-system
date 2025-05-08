package com.movie.entity;

import com.movie.entity.groups.CustomerLogin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

@Data
@Component
public class PlatformManagement {
    private String platformManagementId;
    @NotBlank(message = "账号不能为空",groups = {PlatformManagement.class})
    private String platformManagementAccount;
    @NotBlank(message = "密码不能为空",groups = {PlatformManagement.class})
    @Length(min=6,max = 20,message = "密码长度必须在6到20位之间",groups = {PlatformManagement.class})
    private String platformManagementPassword;
    private String platformManagementName;
    private String platformManagementProfilePicture;
    private String platformToken;
}
