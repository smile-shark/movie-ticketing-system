package com.movie.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MovieTypeMiddleMapper {
    int insertMovieTypeMiddle(@Param("movieId") String movieId,@Param("movieTypeId") String movieTypeId);
}
