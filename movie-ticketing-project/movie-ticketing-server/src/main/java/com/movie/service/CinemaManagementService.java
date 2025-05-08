package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.CinemaManagement;
import com.movie.entity.EmailVerify;

public interface CinemaManagementService {
    Result register(EmailVerify emailVerify);
    Result login(CinemaManagement cinemaManagement);
    Result selectAllCinemaManagement(Integer page, Integer size);
}
