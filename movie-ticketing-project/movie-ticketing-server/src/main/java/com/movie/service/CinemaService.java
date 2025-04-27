package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Cinema;

public interface CinemaService {
    Result selectCinemaByCinemaManagementId(String cinemaManagementId);
    Result insertCinema(Cinema cinema);
}
