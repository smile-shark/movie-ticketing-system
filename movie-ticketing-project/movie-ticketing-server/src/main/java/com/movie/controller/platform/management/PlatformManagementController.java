package com.movie.controller.platform.management;

import com.movie.common.resp.Result;
import com.movie.entity.*;
import com.movie.entity.groups.*;
import com.movie.service.*;
import com.movie.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform")
public class PlatformManagementController {
    private final PlatformManagementService platformManagementService;
    private final MovieTypeService movieTypeService;
    private final MovieService movieService;
    private final UserService userService;
    private final MobileDisplayService mobileDisplayService;

    @PostMapping("/login")
    public Result login(@Validated(PlatformManagementLogin.class) @RequestBody PlatformManagement platformManagement) {
        return platformManagementService.login(platformManagement);
    }
    @GetMapping("/movie/types")
    public Result movieTypes() {
        return movieTypeService.selectAllMovieType();
    }
    @PostMapping("/movie")
    public Result addMovie(@Validated(InsertMovie.class)@RequestBody Movie movie) {
        return movieService.insertMovie(movie);
    }
    @GetMapping("/movie/list")
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
    @PutMapping("/movie")
    public Result updateMovie(@Validated(UpdateMovie.class)@RequestBody Movie movie){
        return movieService.updateMovie(movie);
    }
    @GetMapping("/movie/type/by/name")
    public Result selectMovieType(@RequestParam("movieTypeName") String movieTypeName){
        return movieTypeService.selectMovieTypesByName(movieTypeName);
    }
    @PostMapping("/movie/type")
    public Result insertMovieType(@RequestParam("movieTypeName") String movieTypeName){
        return movieTypeService.insertMovieType(movieTypeName);
    }
    @DeleteMapping("/movie/type")
    public Result deleteMovieType(@RequestParam("movieTypeId") String movieTypeId){
        return movieTypeService.deleteMovieTypeById(movieTypeId);
    }
    @PostMapping("/user")
    public Result selectUsersByUser(@RequestBody(required = false) User user,
                                    @RequestParam("size")Integer size,
                                    @RequestParam("page")Integer page){
        return userService.selectUsersByUser(user,size,page);
    }
    @PutMapping("/user")
    public Result updateUserByUserId(@Validated(UpdateUserMarket.class)@RequestBody User user){
        return userService.updateUserByUserId(user);
    }

    /**
     * @deprecated 管理员查看电影的轮动图
     * @return Result
     */
    @GetMapping("/mobile/display")
    public Result selectAllAllowedMobileDisplays(){
        return mobileDisplayService.selectAllAllowedMobileDisplays();
    }
    @PostMapping("/mobile/display")
    public Result insertMobileDisplay(@Validated(MobileDisplayInsert.class)@RequestBody MobileDisplay mobileDisplay){
        return mobileDisplayService.insertMobileDisplay(mobileDisplay);
    }
    @PutMapping("/mobile/display")
    public Result updateMobileDisplay(@Validated(MobileDisplayUpdate.class)@RequestBody MobileDisplay mobileDisplay){
        return mobileDisplayService.updateMobileDisplay(mobileDisplay);
    }
    @DeleteMapping("/mobile/display")
    public Result deleteMobileDisplay(@Validated(MobileDisplayDelete.class)@RequestBody MobileDisplay mobileDisplay){
        return mobileDisplayService.deleteMobileDisplay(mobileDisplay.getMobileDisplayImage());
    }
    @GetMapping("/movie/simple/list")
    public Result selectSimpleMovieList(){
        return movieService.selectSimpleMovieList();
    }
}
