package com.movie.mapper;

import com.movie.entity.Economize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EconomizeMapper {
    List<Economize> selectAllEconomize();
}
