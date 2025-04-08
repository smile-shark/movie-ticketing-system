package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;

public interface EmailVerifyService {
    public Result sentEmail(EmailVerify emailVerify);
}
