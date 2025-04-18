package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Movie;

public interface MovieService {
     Result insertMovie(Movie movie);
     Result selectMovieList(Movie movie, Integer already, Integer size, Integer page);
     Result updateMovie(Movie movie);
}
