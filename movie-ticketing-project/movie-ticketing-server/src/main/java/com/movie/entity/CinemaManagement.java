package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CinemaManagement {
    private String cinemaManagementId;
    private String cinemaManagementEmail;
    private String cinemaManagementPassword;
    private String cinemaManagementProfilePicture;
}
