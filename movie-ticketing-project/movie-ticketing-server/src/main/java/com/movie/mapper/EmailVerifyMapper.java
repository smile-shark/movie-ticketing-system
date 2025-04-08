package com.movie.mapper;

import com.movie.entity.EmailVerify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailVerifyMapper {
    int insertEmailVerify(@Param("emailVerify")EmailVerify emailVerify);
}
