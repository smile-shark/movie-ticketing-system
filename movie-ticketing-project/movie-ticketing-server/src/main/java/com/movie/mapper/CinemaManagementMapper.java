package com.movie.mapper;

import com.movie.entity.CinemaManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CinemaManagementMapper {
    CinemaManagement selectCinemaManagementByEmail(@Param("email")String email);
    int insertCinemaManagement(@Param("cinemaManagement")CinemaManagement cinemaManagement);
    List<CinemaManagement> selectAllCinemaManagement();
}
