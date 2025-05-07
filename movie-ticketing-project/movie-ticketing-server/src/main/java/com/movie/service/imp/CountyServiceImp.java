package com.movie.service.imp;

import com.github.pagehelper.PageHelper;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.County;
import com.movie.exception.BusinessException;
import com.movie.mapper.CountyMapper;
import com.movie.service.CountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountyServiceImp implements CountyService {
    private final CountyMapper countyMapper;

    @Override
    public Result selectCountyByMarketId(String marketId) {
        try {
            List<County> counties = countyMapper.selectCountyByMarketId(marketId);
            return Result.success(RespCode.FIND_SUCCESS,counties);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
    }

    @Override
    public Result selectCountyByCountyId(String countyId) {
        try {
            County county = countyMapper.selectCountyByCountyId(countyId);
            return Result.success(RespCode.FIND_SUCCESS,county);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
    }
}
