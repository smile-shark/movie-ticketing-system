package com.movie.mapper;

import com.movie.entity.Cinema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CinemaMapper {
    List<Cinema> selectCinemaByCinemaManagementId(@Param("cinemaManagementId")String cinemaManagementId);
    int insertCinema(@Param("cinema")Cinema cinema);

}
