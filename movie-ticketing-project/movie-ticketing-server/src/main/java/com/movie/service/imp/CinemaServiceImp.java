package com.movie.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Cinema;
import com.movie.exception.BusinessException;
import com.movie.mapper.CinemaMapper;
import com.movie.mapper.SliceArrangementMapper;
import com.movie.service.CinemaService;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaServiceImp implements CinemaService {
    private final CinemaMapper cinemaMapper;

    @Override
    public Result selectCinemaByCinemaManagementId(String cinemaManagementId) {
        try {
            List<Cinema> cinemas = cinemaMapper.selectCinemaByCinemaManagementId(cinemaManagementId);
            return Result.success(RespCode.FIND_SUCCESS, cinemas);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result insertCinema(Cinema cinema) {
        try {
            cinema.setCinemaId(UUIDUtils.generateUUID());
            if (cinemaMapper.insertCinema(cinema) > 0) {
                return Result.success(RespCode.ADD_SUCCESS);
            } else {
                return Result.error(RespCode.ADD_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result customerSelectLowPriceCinema(String countyId, String cinemaBrandId, String movieId, Integer page, Integer size) {
        try {
            Page<Object> objects = PageHelper.startPage(page, size);
            List<Cinema> cinemas = cinemaMapper.customerSelectLowPriceCinema(countyId, cinemaBrandId, movieId);
            return Result.success(RespCode.FIND_SUCCESS, PageInfo.of(objects));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectCinemaByCinemaId(String cinemaId) {
        try{
            Cinema cinema = cinemaMapper.selectCinemaByCinemaId(cinemaId);
            return Result.success(RespCode.FIND_SUCCESS, cinema);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
