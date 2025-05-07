package com.movie.mapper;

import com.movie.entity.ScreeningRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScreeningRoomMapper {
    int insertScreeningRoom(@Param("screeningRoom")ScreeningRoom screeningRoom);
    List<ScreeningRoom> selectScreeningRoomByCinemaId(@Param("cinemaId") String cinemaId);
    int updateScreeningRoom(@Param("screeningRoom")ScreeningRoom screeningRoom);
    List<ScreeningRoom> selectSimpleScreeningRoom(@Param("cinemaId") String cinemaId);
    ScreeningRoom selectScreeningRoomByScreeningRoomId(@Param("screeningRoomId") String screeningRoomId);
}
