package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.MobileDisplay;

public interface MobileDisplayService {
    Result selectAllAllowedMobileDisplays();

    Result insertMobileDisplay(MobileDisplay mobileDisplay);

    Result updateMobileDisplay(MobileDisplay mobileDisplay);

    Result deleteMobileDisplay(String image);
}
