package com.movie.entity;

import com.movie.entity.groups.InsertSliceArrangement;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class SliceArrangement {
    private String sliceArrangementId;
    @NotNull(message = "影院id不能为空",groups = {InsertSliceArrangement.class})
    @Length(min=32,max = 32,message = "影院id格式不符合规范",groups = {InsertSliceArrangement.class})
    private String cinemaId;
    private Cinema cinema;
    @NotNull(message = "电影id不能为空",groups = {InsertSliceArrangement.class})
    @Length(min=32,max = 32,message = "电影id格式不符合规范",groups = {InsertSliceArrangement.class})
    private String movieId;
    private Movie movie;
    @NotNull(message = "场次id不能为空",groups = {InsertSliceArrangement.class})
    @Length(min=32,max = 32,message = "场次id格式不符合规范",groups = {InsertSliceArrangement.class})
    private String screeningRoomId;
    private ScreeningRoom screeningRoom;
    @NotNull(message = "票价不能为空",groups = {InsertSliceArrangement.class})
    @Min(value = 0,message = "票价不能为负数",groups = {InsertSliceArrangement.class})
    private Double votePrice;
    @NotNull(message = "布局不能为空",groups = {InsertSliceArrangement.class})
    @Length(min=1,message = "布局格式不符合规范",groups = {InsertSliceArrangement.class})
    private String layout;
    @NotNull(message = "开始时间不能为空",groups = {InsertSliceArrangement.class})
    private LocalDateTime sliceArrangementStartTime;
    @NotNull(message = "结束时间不能为空",groups = {InsertSliceArrangement.class})
    private LocalDateTime sliceArrangementEndTime;
}
