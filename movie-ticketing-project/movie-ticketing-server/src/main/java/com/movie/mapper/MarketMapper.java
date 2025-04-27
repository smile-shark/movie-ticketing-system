package com.movie.mapper;

import com.movie.entity.Market;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MarketMapper {
    List<Market> selectAllMarket();
    Market selectMarketById(String marketId);
    List<Market> selectMarketByEconomizeId(@Param("economizeId")String economizeId);
}
