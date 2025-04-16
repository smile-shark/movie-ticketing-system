package com.movie.mapper;

import com.movie.entity.PlatformManagement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlatformManagementMapper {
    PlatformManagement selectByAccount(String account);
}
