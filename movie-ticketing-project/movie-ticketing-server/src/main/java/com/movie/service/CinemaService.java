package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Cinema;

public interface CinemaService {
    Result selectCinemaByCinemaManagementId(String cinemaManagementId);
    Result insertCinema(Cinema cinema);

    /**
     * @Description 1. 根据所属地区查询
     * @Description 2. 根据影院的品牌查询
     * @Description 3. 根据影院的评分来倒序排序
     * @Description 4. 需要有当前电影的有效排片
     * @Description 4. 取排片最低价格的信息
     * @param countyId 影院所属的地区
     * @param cinemaBrandId 影院对应的品牌
     * @param movieId 用户所选择的电影
     * @return 最低票价的影院信息列表
     */
    Result customerSelectLowPriceCinema(String countyId,String cinemaBrandId,String movieId,Integer page,Integer size);

    Result selectCinemaByCinemaId(String cinemaId);
}
