package com.movie.controller.platform.management;

import com.movie.common.resp.Result;
import com.movie.entity.MovieType;
import com.movie.entity.PlatformManagement;
import com.movie.entity.groups.PlatformManagementLogin;
import com.movie.service.MovieTypeService;
import com.movie.service.PlatformManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform")
public class PlatformManagementController {
    private final PlatformManagementService platformManagementService;
    private final MovieTypeService movieTypeService;
    @PostMapping("/login")
    public Result login(@Validated(PlatformManagementLogin.class) @RequestBody PlatformManagement platformManagement) {
        return platformManagementService.login(platformManagement);
    }
    @GetMapping("/movie/types")
    public Result movieTypes() {
        return movieTypeService.selectAllMovieType();
    }
}
