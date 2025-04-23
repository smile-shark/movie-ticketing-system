package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.CinemaManagement;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.exception.BusinessException;
import com.movie.mapper.CinemaManagementMapper;
import com.movie.mapper.EmailVerifyMapper;
import com.movie.mapper.GlobalProfilePictureMapper;
import com.movie.service.CinemaManagementService;
import com.movie.service.EmailVerifyService;
import com.movie.utils.MD5Utils;
import com.movie.utils.TokenUtils;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.ognl.Token;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaManagementServiceImp implements CinemaManagementService {
    private final CinemaManagementMapper cinemaManagementMapper;
    private final EmailVerifyService emailVerifyService;
    private final MD5Utils md5Utils;
    private final GlobalProfilePictureMapper globalProfilePictureMapper;
    private final TokenUtils tokenUtils;


    @Override
    public Result register(EmailVerify emailVerify) {
        // 1. 查看数据库中是否账号已存在
        CinemaManagement cinemaManagement = cinemaManagementMapper.selectCinemaManagementByEmail(emailVerify.getEmail());

        if(cinemaManagement != null){
            throw new BusinessException(RespCode.USER_ALREADY_EXISTS);
        }
        emailVerifyService.verifyEmail(emailVerify);
        // 创建id，加密密码
        String cinemaManagementId = UUIDUtils.generateUUID();
        CinemaManagement management = new CinemaManagement() {{
            setCinemaManagementId(cinemaManagementId);
            setCinemaManagementEmail(emailVerify.getEmail());
            setCinemaManagementProfilePicture(
                    globalProfilePictureMapper.selectGlobalProfilePictureRandom()
            );
            setCinemaManagementPassword(md5Utils.md5Encrypt(emailVerify.getPassword(), cinemaManagementId));
        }};
        int i = cinemaManagementMapper.insertCinemaManagement(management);
        if(i < 1){
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
        return Result.success(RespCode.REGISTER_USER_SUCCESS);
    }

    @Override
    public Result login(CinemaManagement cinemaManagement) {
        // 1. 查询是否有该用户
        CinemaManagement have = cinemaManagementMapper.selectCinemaManagementByEmail(cinemaManagement.getCinemaManagementEmail());

        if(have == null){
            throw new BusinessException(RespCode.NOT_FOUND_USER);
        }
        // 2. 将密码加密后与数据库中的密码进行比对
        String password = md5Utils.md5Encrypt(cinemaManagement.getCinemaManagementPassword() , have.getCinemaManagementId());
        if(!have.getCinemaManagementPassword().equals(password)){
            throw new BusinessException(RespCode.PASSWORD_ERROR);
        }
        have.setCinemaManagementPassword(null);
        have.setToken(tokenUtils.createToken(have));
        // 3. 登录成功
        return Result.success(RespCode.LOGIN_USER_SUCCESS,have);
    }
}
