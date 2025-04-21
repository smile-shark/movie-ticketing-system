package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class Advertisement {
    private String advertisementId;
    private String advertisementImage;
    private String advertisementUrl;
    private LocalDateTime advertisementEndTime;
}
