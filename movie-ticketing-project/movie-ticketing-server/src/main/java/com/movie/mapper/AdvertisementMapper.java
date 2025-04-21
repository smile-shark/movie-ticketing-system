package com.movie.mapper;

import com.movie.entity.Advertisement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdvertisementMapper {
    Advertisement selectOneAdvertisementByRandom();
}
