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
        try {
            mobileDisplays = mobileDisplayMapper.selectAllAllowedMobileDisplays();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
        return Result.success(RespCode.FIND_SUCCESS).setData(mobileDisplays);
    }

    @Override
    public Result insertMobileDisplay(MobileDisplay mobileDisplay) {
        try {
            if (mobileDisplayMapper.insertMobileDisplay(mobileDisplay) > 0) {
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
    public Result updateMobileDisplay(MobileDisplay mobileDisplay) {
        try {
            if (mobileDisplayMapper.updateMobileDisplay(mobileDisplay) > 0) {
                return Result.success(RespCode.UPDATE_SUCCESS);
            } else {
                return Result.error(RespCode.UPDATE_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result deleteMobileDisplay(String image) {
        try {
            if (mobileDisplayMapper.deleteMobileDisplay(image) > 0) {
                return Result.success(RespCode.DELETE_SUCCESS);
            } else {
                return Result.error(RespCode.DELETE_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
