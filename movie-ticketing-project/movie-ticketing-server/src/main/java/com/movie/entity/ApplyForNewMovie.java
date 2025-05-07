package com.movie.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class ApplyForNewMovie {
    private String applyId;
    private String platformManagementId;
    private String cinemaManagementId;
    private String applyReason;
    private String movieName;
    private LocalDateTime movieStartTime;
    private String movieDuration;
    private String director;
    private String star;
    private String introduction;
    private Double score;
    private String posterImage;
    private String issuingRegion;
    private Integer handleState;
}
