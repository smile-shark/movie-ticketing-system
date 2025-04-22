package com.movie.mapper;

import com.movie.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User selectUserByEmail(@Param("userEmail") String userEmail);
    int insertUser(@Param("user") User user);
    int updateUserByUserId(@Param("user") User user);
    List<User> selectUsersByUser(@Param("user") User user);
}
