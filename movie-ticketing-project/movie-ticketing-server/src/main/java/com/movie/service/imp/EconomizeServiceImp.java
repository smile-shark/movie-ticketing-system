package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Economize;
import com.movie.mapper.EconomizeMapper;
import com.movie.service.EconomizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EconomizeServiceImp implements EconomizeService {
    private final EconomizeMapper economizeMapper;

    @Override
    public Result selectAllEconomize() {
        try {
            List<Economize> economizes = economizeMapper.selectAllEconomize();
            return Result.success(RespCode.FIND_SUCCESS,economizes);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(RespCode.DATABASE_ERROR);
        }
    }
}
