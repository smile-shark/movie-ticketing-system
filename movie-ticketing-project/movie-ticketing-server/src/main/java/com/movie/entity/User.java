package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    private String userId;
    private String countyId;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userProfilePicture;
    private Integer userPoints;
    private Integer userLevel;
    private Integer userState;
}
