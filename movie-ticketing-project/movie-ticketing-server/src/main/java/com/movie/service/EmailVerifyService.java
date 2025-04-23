package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;

public interface EmailVerifyService {
    Result sentEmail(EmailVerify emailVerify);
    void verifyEmail(EmailVerify emailVerify);
}
