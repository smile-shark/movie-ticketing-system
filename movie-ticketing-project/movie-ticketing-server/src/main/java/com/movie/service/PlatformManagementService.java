package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.PlatformManagement;

public interface PlatformManagementService {
    Result login(PlatformManagement platformManagement);
}
