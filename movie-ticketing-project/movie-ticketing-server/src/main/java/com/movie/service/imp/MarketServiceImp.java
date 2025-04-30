package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Market;
import com.movie.exception.BusinessException;
import com.movie.mapper.MarketMapper;
import com.movie.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketServiceImp implements MarketService {
    private final MarketMapper marketMapper;

    @Override
    public Result selectAllMarket() {
        try{
            List<Market> markets = marketMapper.selectAllMarket();
            Result success = Result.success(RespCode.FIND_SUCCESS, markets);
            System.out.println(success);
            return success;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(RespCode.FIND_ERROR.getMessage());
        }
    }

    @Override
    public Result selectMarketByEconomizeId(String economizeId) {
        try {
            List<Market> markets = marketMapper.selectMarketByEconomizeId(economizeId);
            return Result.success(RespCode.FIND_SUCCESS,markets);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
    }
}
