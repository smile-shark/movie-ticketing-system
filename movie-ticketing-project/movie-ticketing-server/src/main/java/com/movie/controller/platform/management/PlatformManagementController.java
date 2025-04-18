package com.movie.controller.platform.management;

import com.movie.common.resp.Result;
import com.movie.entity.Movie;
import com.movie.entity.MovieType;
import com.movie.entity.PlatformManagement;
import com.movie.entity.groups.InsertMovie;
import com.movie.entity.groups.PlatformManagementLogin;
import com.movie.entity.groups.UpdateMovie;
import com.movie.service.MovieService;
import com.movie.service.MovieTypeService;
import com.movie.service.PlatformManagementService;
import com.movie.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/platform")
public class PlatformManagementController {
    private final PlatformManagementService platformManagementService;
    private final MovieTypeService movieTypeService;
    private final MovieService movieService;
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
}
