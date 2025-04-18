package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.MobileDisplay;
import com.movie.exception.BusinessException;
import com.movie.mapper.MobileDisplayMapper;
import com.movie.service.MobileDisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MobileDisplayServiceImp implements MobileDisplayService {
    private final MobileDisplayMapper mobileDisplayMapper;

    @Override
    public Result selectAllAllowedMobileDisplays() {
        List<MobileDisplay> mobileDisplays;
        try{
            mobileDisplays= mobileDisplayMapper.selectAllAllowedMobileDisplays();
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
        return Result.success(RespCode.FIND_SUCCESS).setData(mobileDisplays);
    }
}
