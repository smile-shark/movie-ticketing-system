package com.movie.mapper;

import com.movie.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MovieMapper {
    int insertMovie(@Param("movie") Movie movie);
}
