package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.User;

public interface UserService {
     Result register(EmailVerify emailVerify);
     Result login(User user);
     Result updateUserByUserId(User user);
}
