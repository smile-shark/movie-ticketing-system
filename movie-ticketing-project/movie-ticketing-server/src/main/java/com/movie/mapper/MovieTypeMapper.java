package com.movie.mapper;

import com.movie.entity.MovieType;
import com.movie.entity.MovieTypeMiddle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieTypeMapper {
    List<MovieType> selectAllMovieType();
    List<MovieType> selectMovieTypesInMovieTypeIds(@Param("movieTypeMiddles") List<MovieTypeMiddle> movieTypeMiddles);
}
