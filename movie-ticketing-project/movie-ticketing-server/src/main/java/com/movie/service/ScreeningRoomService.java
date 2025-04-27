package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.ScreeningRoom;

public interface ScreeningRoomService {
    Result insertScreeningRoom(ScreeningRoom screeningRoom);
    Result selectScreeningRoomByCinemaId(String cinemaId);
    Result updateScreeningRoom(ScreeningRoom screeningRoom);
}
