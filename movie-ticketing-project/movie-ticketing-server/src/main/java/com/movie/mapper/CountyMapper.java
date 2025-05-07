package com.movie.mapper;

import com.movie.entity.County;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CountyMapper {
    List<County> selectCountyByMarketId(@Param("marketId")String marketId);
    County selectCountyByCountyId(@Param("countyId")String countyId);
}
