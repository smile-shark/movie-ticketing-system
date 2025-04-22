package com.movie.mapper;

import com.movie.entity.MobileDisplay;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MobileDisplayMapper {
    List<MobileDisplay> selectAllAllowedMobileDisplays();
    int insertMobileDisplay(@Param("mobileDisplay") MobileDisplay mobileDisplay);
    int updateMobileDisplay(@Param("mobileDisplay") MobileDisplay mobileDisplay);
    int deleteMobileDisplay(@Param("image") String image);
}
