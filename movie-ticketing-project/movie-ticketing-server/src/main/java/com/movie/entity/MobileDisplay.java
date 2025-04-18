package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class MobileDisplay {
    private String movieId;
    private Movie movie;
    private String mobileDisplayImage;
    private LocalDateTime mobileDisplayEndTime;
}
