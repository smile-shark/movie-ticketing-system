package com.movie.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyForNewMovieMapper {
    int applyForNewMovieTotal();
}
