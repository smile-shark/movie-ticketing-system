package com.movie.mapper;

import com.movie.entity.MovieTypeMiddle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieTypeMiddleMapper {
    int insertMovieTypeMiddle(@Param("movieId") String movieId,@Param("movieTypeId") String movieTypeId);
    List<MovieTypeMiddle> selectMovieTypeMiddlesByMovieId(@Param("movieId") String movieId);
    int deleteMovieTypeMiddlesByMovieId(@Param("movieId") String movieId);
}
