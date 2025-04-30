package com.movie.service.imp;

import cn.hutool.jwt.JWTUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.exception.BusinessException;
import com.movie.mapper.EmailVerifyMapper;
import com.movie.mapper.GlobalProfilePictureMapper;
import com.movie.mapper.MarketMapper;
import com.movie.mapper.UserMapper;
import com.movie.service.EmailVerifyService;
import com.movie.service.UserService;
import com.movie.utils.MD5Utils;
import com.movie.utils.RandomNameUtil;
import com.movie.utils.TokenUtils;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final EmailVerifyService emailVerifyService;
    private final UserMapper userMapper;
    private final MD5Utils md5Utils;
    private final RandomNameUtil randomNameUtil;
    private final MarketMapper marketMapper;
    private final TokenUtils tokenUtils;
    private final GlobalProfilePictureMapper globalProfilePictureMapper;
    @Override
    public Result register(EmailVerify emailVerify) {
        // 1. 查看数据库中是否账号已存在
        User user = userMapper.selectUserByEmail(emailVerify.getEmail());

        if(user!= null){
            throw new BusinessException(RespCode.USER_ALREADY_EXISTS);
        }
        emailVerifyService.verifyEmail(emailVerify);
        // 创建id，加密密码
        String userId = UUIDUtils.generateUUID();
        User newUser = new User() {{
            setUserId(userId);
            setUserEmail(emailVerify.getEmail());
            setUserName(randomNameUtil.generateRandomName());
            setUserProfilePicture(
                    globalProfilePictureMapper.selectGlobalProfilePictureRandom()
            );
            setUserPassword(md5Utils.md5Encrypt(
                    emailVerify.getPassword(),userId
            ));
        }};
        int i = userMapper.insertUser(newUser);
        if(i < 1){
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
        return Result.success(RespCode.REGISTER_USER_SUCCESS);
    }

    @Override
    public Result login(User user) {
        // 1. 查询是否有该用户
        User have = userMapper.selectUserByEmail(user.getUserEmail());
        if(have == null){
            throw new BusinessException(RespCode.NOT_FOUND_USER);
        }
        // 2. 将密码加密后与数据库中的密码进行比对
        String password = md5Utils.md5Encrypt(user.getUserPassword() , have.getUserId());
        if(!have.getUserPassword().equals(password)){
            throw new BusinessException(RespCode.PASSWORD_ERROR);
        }
        have.setUserPassword(null);
        if(have.getMarketId() != null){
            have.setMarket(marketMapper.selectMarketById(have.getMarketId()));
        }
        have.setToken(tokenUtils.createToken(have));
        // 3. 登录成功
        return Result.success(RespCode.LOGIN_USER_SUCCESS,have);
    }

    @Override
    public Result updateUserByUserId(User user) {
        try {
            if(userMapper.updateUserByUserId(user)<=0){
                throw new BusinessException(RespCode.UPDATE_USER_INFO_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.UPDATE_USER_INFO_ERROR);
        }
        return Result.success(RespCode.UPDATE_USER_INFO_SUCCESS);
    }

    @Override
    public Result selectUsersByUser(User user,Integer size,Integer page) {
        try {
            Page<Object> objects = PageHelper.startPage(page, size);
            List<User> users = userMapper.selectUsersByUser(user);
            return Result.success(RespCode.FIND_SUCCESS,PageInfo.of(objects));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
    }
}
