package com.movie.service;

import com.movie.common.resp.Result;

public interface MarketService {
    Result selectAllMarket();
    Result selectMarketByEconomizeId(String economizeId);
}
