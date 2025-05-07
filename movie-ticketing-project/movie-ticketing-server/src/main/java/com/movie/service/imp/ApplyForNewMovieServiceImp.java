package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.exception.BusinessException;
import com.movie.mapper.ApplyForNewMovieMapper;
import com.movie.service.ApplyForNewMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplyForNewMovieServiceImp implements ApplyForNewMovieService {
    private final ApplyForNewMovieMapper applyForNewMovieMapper;


    @Override
    public Result applyForNewMovieTotal() {
        try {
            return Result.success(RespCode.FIND_SUCCESS,applyForNewMovieMapper.applyForNewMovieTotal());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
