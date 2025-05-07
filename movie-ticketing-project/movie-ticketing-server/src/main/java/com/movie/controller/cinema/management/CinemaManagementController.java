package com.movie.controller.cinema.management;


import com.movie.common.resp.Result;
import com.movie.entity.*;
import com.movie.entity.groups.*;
import com.movie.service.*;
import com.movie.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
    private final ScreeningRoomService screeningRoomService;
    private final MovieService movieService;
    private final SliceArrangementService sliceArrangementService;
    private final OrderService orderService;

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
    public Result insertCinema(@Validated(InsertCinema.class) @RequestBody Cinema cinema){
        return cinemaService.insertCinema(cinema);
    }
    @PostMapping("/screening-room")
    public Result insertScreeningRoom(@Validated(InsertScreeningRoom.class)@RequestBody ScreeningRoom screeningRoom){
        return screeningRoomService.insertScreeningRoom(screeningRoom);
    }
    @GetMapping("/screening-room/by/cinema/id")
    public Result selectScreeningRoomByCinemaId(@RequestParam("cinemaId") String cinemaId){
        return screeningRoomService.selectScreeningRoomByCinemaId(cinemaId);
    }
    @PutMapping("/screening-room")
    public Result updateScreeningRoom(@Validated(UpdateScreeningRoom.class)@RequestBody ScreeningRoom screeningRoom){
       return screeningRoomService.updateScreeningRoom(screeningRoom);
    }
    @GetMapping("/movie/list")
    public Result selectMovieList(
            @RequestParam(value = "random", required = false) Boolean random,
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
                random,
                already,
                size,
                page
        );
    }
    @GetMapping("/simple/movie/list")
    public Result selectSimpleMovieList(){
        return movieService.selectSimpleMovieList();
    }
    @GetMapping("/simple/screening-room/list")
    public Result selectSimpleScreeningRoom(){
        return screeningRoomService.selectSimpleScreeningRoom();
    }
    @PostMapping("/slice-arrangement")
    public Result insertSliceArrangement(@Validated(InsertSliceArrangement.class) @RequestBody SliceArrangement sliceArrangement){
        return sliceArrangementService.insertSliceArrangement(sliceArrangement);
    }
    @GetMapping("/screening-room/by/screening-room-id")
    public Result selectScreeningRoomByScreeningRoomId(@RequestParam("screeningRoomId") String screeningRoomId){
        return screeningRoomService.selectScreeningRoomByScreeningRoomId(screeningRoomId);
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
    @GetMapping("/order/by/slice-arrangement-id")
    public Result selectOrderBySliceArrangementId(
            @RequestParam("sliceArrangementId") String sliceArrangementId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size){
        return orderService.selectOrderBySliceArrangementId(sliceArrangementId,page,size);
    }
    @GetMapping("/order/valid/num/by/time")
    public Result selectOrderNumByTime(
            @RequestParam("cinemaId") String cinemaId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime){
        return orderService.selectOrderNumByTime(cinemaId,startTime,endTime);
    }
    @GetMapping("/slice-arrangement/num/by/time")
    public Result selectSliceArrangementNumByTime(
            @RequestParam("cinemaId") String cinemaId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime)
    {
        return sliceArrangementService.selectSliceArrangementNumByTime(cinemaId,startTime,endTime);
    }
    @GetMapping("/order/vote-all-price/valid/by/time")
    public Result selectOrderVoteAllPriceByTime(
            @RequestParam("cinemaId") String cinemaId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime)
    {
        return orderService.selectOrderVoteAllPriceByTime(cinemaId,startTime,endTime);
    }
    @GetMapping("/order/vote-all-price/all")
    public Result selectAllOrderPrice(@RequestParam("cinemaId") String cinemaId){
        return orderService.selectAllOrderPrice(cinemaId);
    }
    @GetMapping("/movie-revenue/by/time")
    public Result selectChartValueByTimeAndMovie(
            @RequestParam("cinemaId") String cinemaId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime
    ){
        return orderService.selectChartValueByTime(cinemaId,startTime,endTime);
    }
    @GetMapping("/screening-room-revenue/by/time")
    public Result selectChartValueByTimeAndScreeningRoom(
            @RequestParam("cinemaId") String cinemaId,
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime
    ){
        return orderService.selectChartValueByTimeAndScreeningRoom(cinemaId,startTime,endTime);
    }
}
