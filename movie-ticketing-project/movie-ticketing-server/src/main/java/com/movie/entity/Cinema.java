package com.movie.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Data
@Component
public class Cinema {
    private String cinemaId;
    private String cinemaBrandId;
    private CinemaBrand cinemaBrand;
    private String cinemaManagementId;
    private String countyId;
    private County county;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaPhone;
    private String cinemaEmail;
    private String cinemaUrl;
    private Integer cinemaPoints; // 影院的评分
    private LocalTime cinemaStartTime; // 影院开业时间
    private LocalTime cinemaEndTime; // 影院闭业时间
}
