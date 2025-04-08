package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;
import com.movie.mapper.EmailVerifyMapper;
import com.movie.mapper.UserMapper;
import com.movie.service.EmailVerifyService;
import com.movie.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerifyServiceImp implements EmailVerifyService {
    private final EmailVerifyMapper emailVerifyMapper;
    private final UserMapper userMapper;
    private final EmailUtils emailUtils;
    @Override
    public Result sentEmail(EmailVerify emailVerify) {
        // 判断邮箱是否注册
        User user = userMapper.selectUserByEmail(emailVerify.getEmail());
        if (user != null) {
            return Result.error(RespCode.USER_ALREADY_EXISTS);
        }
        // 生成验证码
        // 将验证码和对应邮箱添加到数据库中，并且创建有效时间为当前时间加5分钟的验证码记录
        String verifyCode = generateFourDigitCode();
        emailVerify.setEmailVerifyCode(verifyCode);
        emailVerify.setEndTime(LocalDateTime.now().plusMinutes(5));
        int i = emailVerifyMapper.insertEmailVerify(emailVerify);
        if (i <= 0) {
            return Result.error(RespCode.DATABASE_ERROR);
        }
         // 发送验证码
        boolean b = emailUtils.sendVerificationCode(emailVerify.getEmail(), verifyCode);
        if (!b) {
            return Result.error(RespCode.SEND_EMAIL_ERROR);
        }
        return Result.success(RespCode.SEND_EMAIL_SUCCESS);
    }

    public String generateFourDigitCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            // 生成0-9的随机数字
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
