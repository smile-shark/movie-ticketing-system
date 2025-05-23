package com.movie.mapper;

import com.movie.entity.SliceArrangement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface SliceArrangementMapper {
    int insertSliceArrangement(@Param("sliceArrangement")SliceArrangement sliceArrangement);
    List<SliceArrangement> selectSliceArrangementByScreeningRoomIdAndBetweenTime(
            @Param("screeningRoomId") String screeningRoomId,
            @Param("startTime") LocalDateTime startTime, // 新添加的排片不能在上一个排片的结束之前
            @Param("endTime") LocalDateTime endTime
    );
    List<SliceArrangement> selectSliceArrangementBySliceArrangement(@Param("sliceArrangement") SliceArrangement sliceArrangement);
    int updateSliceArrangementLayout(@Param("layout") String layout,@Param("sliceArrangementId")String sliceArrangementId);
    String selectLayoutBySliceArrangementId(@Param("sliceArrangementId")String sliceArrangementId);
    SliceArrangement selectDetailSliceArrangementBySliceArrangementId(@Param("sliceArrangementId")String sliceArrangementId);
    int selectSliceArrangementNumByTime(@Param("cinemaId")String cinemaId,@Param("startTime")LocalDateTime startTime,@Param("endTime")LocalDateTime endTime);
    int aMonthHasSliceArrangementTotal();
}
