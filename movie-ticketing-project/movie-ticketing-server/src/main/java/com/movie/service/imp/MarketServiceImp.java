package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Market;
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
        List<Market> markets;
        try{
            markets = marketMapper.selectAllMarket();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(RespCode.FIND_ERROR.getMessage());
        }
        return Result.success(RespCode.FIND_SUCCESS)
                .setData(markets);
    }
}
