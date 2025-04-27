package com.movie.mapper;

import com.movie.entity.CinemaBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CinemaBrandMapper {
    List<CinemaBrand> selectAllCinemaBrand();
}
