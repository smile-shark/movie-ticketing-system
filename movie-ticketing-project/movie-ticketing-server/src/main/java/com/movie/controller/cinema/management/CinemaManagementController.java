package com.movie.controller.cinema.management;


import com.movie.common.resp.Result;
import com.movie.entity.CinemaManagement;
import com.movie.entity.EmailVerify;
import com.movie.entity.groups.CustomerLogin;
import com.movie.entity.groups.VerifyEmail;
import com.movie.service.CinemaManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/management")
public class CinemaManagementController {
    private final CinemaManagementService cinemaManagementService;

    @PostMapping("/register")
    public Result register(@Validated(VerifyEmail.class) @RequestBody EmailVerify emailVerify) {
        return cinemaManagementService.register(emailVerify);
    }
    @PostMapping("/login")
    public Result login(@Validated(CustomerLogin.class) @RequestBody CinemaManagement cinemaManagement) {
        return cinemaManagementService.login(cinemaManagement);
    }
}
