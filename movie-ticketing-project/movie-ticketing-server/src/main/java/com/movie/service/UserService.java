package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;

public interface UserService {
    public Result register(EmailVerify emailVerify);
}
