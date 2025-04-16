package com.movie.mapper;

import com.movie.entity.MovieType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieTypeMapper {
    List<MovieType> selectAllMovieType();
}
