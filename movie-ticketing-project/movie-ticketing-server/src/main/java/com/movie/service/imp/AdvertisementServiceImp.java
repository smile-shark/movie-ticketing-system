package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.mapper.AdvertisementMapper;
import com.movie.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImp implements AdvertisementService {
    private final AdvertisementMapper advertisementMapper;
    @Override
    public Result selectOneAdvertisementByRandom() {
        try{
           return Result.success(RespCode.FIND_SUCCESS)
                   .setData(advertisementMapper.selectOneAdvertisementByRandom());
        }catch (Exception e){
            return Result.error(RespCode.DATABASE_ERROR);
        }
    }
}
