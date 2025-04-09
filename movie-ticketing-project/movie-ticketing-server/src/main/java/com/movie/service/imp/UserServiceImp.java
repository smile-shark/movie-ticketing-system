package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.exception.BusinessException;
import com.movie.mapper.EmailVerifyMapper;
import com.movie.mapper.UserMapper;
import com.movie.service.UserService;
import com.movie.utils.MD5Utils;
import com.movie.utils.RandomNameUtil;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final EmailVerifyMapper emailVerifyMapper;
    private final UserMapper userMapper;
    private final MD5Utils md5Utils;
    private final RandomNameUtil randomNameUtil;
    @Override
    public Result register(EmailVerify emailVerify) {
        // 1. 查看数据库中是否账号已存在
        User user = userMapper.selectUserByEmail(emailVerify.getEmail());
        if(user!= null){
            throw new BusinessException(RespCode.USER_ALREADY_EXISTS);
        }
        // 验证邮箱验证码的有效性
        List<EmailVerify> emailVerifies = emailVerifyMapper.selectEmailVerifyByEmail(emailVerify.getEmail());
        if(emailVerifies == null || emailVerifies.isEmpty()){
            throw new BusinessException(RespCode.EMAIL_VERIFY_CODE_LOSE_EFFICACY);
        }
        // 验证邮箱验证码的正确性
        EmailVerify first = emailVerifies.getFirst();
        if(!first.getEmailVerifyCode().equals(emailVerify.getEmailVerifyCode())){
            throw new BusinessException(RespCode.EMAIL_VERIFICATION_CODE_INPUT_ERROR);
        }
        // 创建id，加密密码
        String userId = UUIDUtils.generateUUID();
        User newUser = new User() {{
            setUserId(userId);
            setUserEmail(emailVerify.getEmail());
            setUserName(randomNameUtil.generateRandomName());
            setUserPassword(md5Utils.md5Encrypt(
                    emailVerify.getPassword()+userId
            ));
        }};
        int i = userMapper.insertUser(newUser);
        if(i < 1){
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
        return Result.success(RespCode.REGISTER_USER_SUCCESS);
    }
}
