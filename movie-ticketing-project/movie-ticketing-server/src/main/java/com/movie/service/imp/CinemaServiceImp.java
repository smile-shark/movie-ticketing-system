package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Cinema;
import com.movie.exception.BusinessException;
import com.movie.mapper.CinemaMapper;
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
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result insertCinema(Cinema cinema) {
        try {
            cinema.setCinemaId(UUIDUtils.generateUUID());
            if(cinemaMapper.insertCinema(cinema)>0){
                return Result.success(RespCode.ADD_SUCCESS);
            }else{
                return Result.error(RespCode.ADD_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
