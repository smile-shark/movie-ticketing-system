package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.SliceArrangement;

import java.time.LocalDateTime;

public interface SliceArrangementService {
    Result insertSliceArrangement(SliceArrangement sliceArrangement);
    Result selectSliceArrangementBySliceArrangement(SliceArrangement sliceArrangement,Integer page,Integer size);
    Result selectSliceArrangementNumByTime(String cinemaId, LocalDateTime startTime, LocalDateTime endTime);
}
