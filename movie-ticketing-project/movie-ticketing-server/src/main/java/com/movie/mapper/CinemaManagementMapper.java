package com.movie.mapper;

import com.movie.entity.CinemaManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CinemaManagementMapper {
    CinemaManagement selectCinemaManagementByEmail(@Param("email")String email);
}
