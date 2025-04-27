package com.movie.controller.cinema.management;


import com.movie.common.resp.Result;
import com.movie.entity.Cinema;
import com.movie.entity.CinemaManagement;
import com.movie.entity.EmailVerify;
import com.movie.entity.groups.CustomerLogin;
import com.movie.entity.groups.VerifyEmail;
import com.movie.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/management")
public class CinemaManagementController {
    private final CinemaManagementService cinemaManagementService;
    private final CinemaService cinemaService;
    private final CinemaBrandService cinemaBrandService;
    private final EconomizeService economizeService;
    private final MarketService marketService;
    private final CountyService countyService;

    @PostMapping("/register")
    public Result register(@Validated(VerifyEmail.class) @RequestBody EmailVerify emailVerify) {
        return cinemaManagementService.register(emailVerify);
    }
    @PostMapping("/login")
    public Result login(@Validated(CustomerLogin.class) @RequestBody CinemaManagement cinemaManagement) {
        return cinemaManagementService.login(cinemaManagement);
    }
    @GetMapping("/cinema/by/management/id")
    public Result selectCinemaByCinemaManagementId(@RequestParam("cinemaManagementId") String cinemaManagementId) {
        return cinemaService.selectCinemaByCinemaManagementId(cinemaManagementId);
    }
    @GetMapping("/cinema/brand")
    public Result selectAllCinemaBrand(){
        return cinemaBrandService.selectAllCinemaBrand();
    }
    @GetMapping("/all/economize")
    public Result selectAllEconomize(){
        return economizeService.selectAllEconomize();
    }
    @GetMapping("/market/by/economize")
    public Result selectMarketByEconomizeId(@RequestParam("economizeId") String economizeId){
        return marketService.selectMarketByEconomizeId(economizeId);
    }
    @GetMapping("/county/by/market")
    public Result selectCountyByMarketId(@RequestParam("marketId") String marketId){
        return countyService.selectCountyByMarketId(marketId);
    }
    @PostMapping("/cinema")
    public Result insertCinema(@RequestBody Cinema cinema){
        return cinemaService.insertCinema(cinema);
    }
}
