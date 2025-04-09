package com.movie.mapper;

import com.movie.entity.EmailVerify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmailVerifyMapper {
    int insertEmailVerify(@Param("emailVerify")EmailVerify emailVerify);
    List<EmailVerify> selectEmailVerifyByEmail(@Param("email")String email);
}
