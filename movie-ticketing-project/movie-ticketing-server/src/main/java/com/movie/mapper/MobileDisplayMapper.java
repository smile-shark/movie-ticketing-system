package com.movie.mapper;

import com.movie.entity.MobileDisplay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MobileDisplayMapper {
    List<MobileDisplay> selectAllAllowedMobileDisplays();
}
