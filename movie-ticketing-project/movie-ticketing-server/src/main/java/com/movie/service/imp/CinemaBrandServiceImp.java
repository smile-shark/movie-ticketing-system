package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.CinemaBrand;
import com.movie.exception.BusinessException;
import com.movie.mapper.CinemaBrandMapper;
import com.movie.service.CinemaBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaBrandServiceImp implements CinemaBrandService {
    private final CinemaBrandMapper cinemaBrandMapper;

    @Override
    public Result selectAllCinemaBrand() {
        try {
            List<CinemaBrand> cinemaBrands = cinemaBrandMapper.selectAllCinemaBrand();
            return Result.success(RespCode.FIND_SUCCESS,cinemaBrands);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
