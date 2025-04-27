package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.PlatformManagement;
import com.movie.exception.BusinessException;
import com.movie.mapper.PlatformManagementMapper;
import com.movie.service.PlatformManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformManagementServiceImp implements PlatformManagementService {
    private final PlatformManagementMapper platformManagementMapper;
    @Override
    public Result login(PlatformManagement platformManagement) {
        PlatformManagement selectByAccount = platformManagementMapper.selectByAccount(platformManagement.getPlatformManagementAccount());
        if(selectByAccount == null){
            throw new BusinessException(RespCode.NOT_FOUND_USER);
        }
        if(!selectByAccount.getPlatformManagementPassword().equals(platformManagement.getPlatformManagementPassword())){
            throw new BusinessException(RespCode.PASSWORD_ERROR);
        }
        selectByAccount.setPlatformManagementPassword(null);
        return Result.success(RespCode.LOGIN_USER_SUCCESS,selectByAccount);
    }
}
