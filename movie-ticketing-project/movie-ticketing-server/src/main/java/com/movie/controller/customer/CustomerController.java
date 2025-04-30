package com.movie.controller.customer;

import com.movie.common.resp.Result;
import com.movie.entity.EmailVerify;
import com.movie.entity.Movie;
import com.movie.entity.SliceArrangement;
import com.movie.entity.User;
import com.movie.entity.groups.CustomerLogin;
import com.movie.entity.groups.UpdateUserMarket;
import com.movie.entity.groups.VerifyEmail;
import com.movie.service.*;
import com.movie.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final UserService userService;
    private final MarketService marketService;
    private final MobileDisplayService mobileDisplayService;
    private final MovieService movieService;
    private final AdvertisementService advertisementService;
    private final CinemaBrandService cinemaBrandService;
    private final CountyService countyService;
    private final CinemaService cinemaService;
    private SliceArrangementService sliceArrangementService;


    @PostMapping("/register")
    public Result register(@Validated(VerifyEmail.class) @RequestBody EmailVerify emailVerify) {
       return userService.register(emailVerify);
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
    @GetMapping("/cinema/brand")
    public Result selectAllCinemaBrand(){
        return cinemaBrandService.selectAllCinemaBrand();
    }

    @GetMapping("/county/by/market")
    public Result selectCountyByMarketId(@RequestParam("marketId") String marketId){
        return countyService.selectCountyByMarketId(marketId);
    }
    @GetMapping("/cinema/low/price")
    public Result customerSelectLowPriceCinema(
            @RequestParam(value = "countyId", required = false)String countyId,
            @RequestParam(value = "cinemaBrandId",required = false)String cinemaBrandId,
            @RequestParam("movieId")String movieId,
            @RequestParam("page")Integer page,
            @RequestParam("size")Integer size){
        return cinemaService.customerSelectLowPriceCinema(countyId,cinemaBrandId,movieId,page,size);
    }
    @GetMapping("/cinema/by/cinema-id")
    public Result selectCinemaByCinemaId(@RequestParam("cinemaId")String cinemaId){
        return cinemaService.selectCinemaByCinemaId(cinemaId);
    }
    @GetMapping("/slice-arrangement/by/slice-arrangement")
    public Result selectSliceArrangementBySliceArrangement(
            @RequestParam(value = "sliceArrangementId",required = false) String sliceArrangementId,
            @RequestParam(value = "cinemaId",required = false) String cinemaId,
            @RequestParam(value = "movieId",required = false) String movieId,
            @RequestParam(value = "screeningRoomId",required = false) String screeningRoomId,
            @RequestParam(value = "startTime",required = false) LocalDateTime startTime,
            @RequestParam(value = "endTime",required = false) LocalDateTime endTime,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ){
        return sliceArrangementService.selectSliceArrangementBySliceArrangement(
                new SliceArrangement(){{
                    setSliceArrangementId(sliceArrangementId);
                    setCinemaId(cinemaId);
                    setMovieId(movieId);
                    setScreeningRoomId(screeningRoomId);
                    setSliceArrangementStartTime(startTime);
                    setSliceArrangementEndTime(endTime);
                }},
                page,
                size
        );
    }
}
