package com.movie.entity;

import com.movie.entity.groups.InsertCinema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Data
@Component
public class Cinema {
    private String cinemaId;
    @Length(min=32,max=32,message = "影院品牌ID格式不正确",groups = {InsertCinema.class})
    private String cinemaBrandId;
    private CinemaBrand cinemaBrand;
    @Length(min=32,max=32,message = "影院管理ID格式不正确",groups = {InsertCinema.class})
    private String cinemaManagementId;
    @NotNull(message = "影院所属县区不能为空",groups = {InsertCinema.class})
    @Length(min=32,max=32,message = "地区ID格式不正确",groups = {InsertCinema.class})
    private String countyId;
    private County county;
    @NotNull(message = "影院名称不能为空",groups = {InsertCinema.class})
    private String cinemaName;
    @NotNull(message = "影院地址不能为空",groups = {InsertCinema.class})
    private String cinemaAddress;
    private String cinemaPhone;
    private String cinemaEmail;
    private String cinemaUrl;
    private Integer cinemaPoints; // 影院的评分
    @NotNull(message = "影院开业时间不能为空",groups = {InsertCinema.class})
    private LocalTime cinemaStartTime; // 影院开业时间
    @NotNull(message = "影院闭业时间不能为空",groups = {InsertCinema.class})
    private LocalTime cinemaEndTime; // 影院闭业时间
}
