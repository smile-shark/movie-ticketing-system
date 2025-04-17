package com.movie.mapper;

import com.movie.entity.Market;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MarketMapper {
    List<Market> selectAllMarket();
    Market selectMarketById(String marketId);
}
