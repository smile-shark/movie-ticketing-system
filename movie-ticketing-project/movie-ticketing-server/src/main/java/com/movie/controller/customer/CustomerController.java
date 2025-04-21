package com.movie.controller.customer;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.Movie;
import com.movie.entity.User;
import com.movie.entity.groups.CustomerLogin;
import com.movie.entity.groups.SendEmail;
import com.movie.entity.groups.UpdateUserMarket;
import com.movie.entity.groups.VerifyEmail;
import com.movie.service.*;
import com.movie.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final EmailVerifyService emailVerifyService;
    private final UserService userService;
    private final MarketService marketService;
    private final MobileDisplayService mobileDisplayService;
    private final MovieService movieService;
    private final AdvertisementService advertisementService;
    @PostMapping("/register")
    public Result register(@Validated(VerifyEmail.class) @RequestBody EmailVerify emailVerify) {
       return userService.register(emailVerify);
    }
    @PostMapping("/email/verification/code")
    public Result emailVerificationCode(@Validated(SendEmail.class) @RequestBody EmailVerify emailVerify) {
        return emailVerifyService.sentEmail(emailVerify);
    }
    @PostMapping("/login")
    public Result login(@Validated(CustomerLogin.class) @RequestBody User user) {
        return userService.login(user);
    }
    @GetMapping("/market")
    public Result selectAllMarket(){
        return marketService.selectAllMarket();
    }
    @PutMapping("/user")
    public Result updateUserByUserId(@Validated(UpdateUserMarket.class)@RequestBody User user){
        return userService.updateUserByUserId(user);
    }
    @GetMapping("/mobile/display")
    public Result selectAllAllowedMobileDisplays(){
        return mobileDisplayService.selectAllAllowedMobileDisplays();
    }
    @GetMapping("/movie")
    public Result selectMovieList(
            @RequestParam(value = "movieId", required = false) String movieId,
            @RequestParam(value = "movieName", required = false) String movieName,
            @RequestParam(value = "director", required = false) String director,
            @RequestParam(value = "star", required = false) String star,
            @RequestParam(value = "issuingRegion", required = false) String issuingRegion,
            @RequestParam(value = "already", required = false) Integer already,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "page") Integer page
    ){
        return movieService.selectMovieList(
                new Movie(){{
                    setMovieId(movieId);
                    setMovieName(VagueUtil.vague(movieName));
                    setDirector(director);
                    setStar(star);
                    setIssuingRegion(issuingRegion);
                }},
                already,
                size,
                page
        );
    }
    @GetMapping("/advertisement")
    public Result selectOneAdvertisementByRandom(){
        return advertisementService.selectOneAdvertisementByRandom();
    }
}
