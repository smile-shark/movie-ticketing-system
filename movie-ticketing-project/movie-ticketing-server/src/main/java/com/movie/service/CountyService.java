package com.movie.service;

import com.movie.common.resp.Result;

public interface CountyService {
    Result selectCountyByMarketId(String marketId);
    Result selectCountyByCountyId(String countyId);
}
