package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Movie;

public interface MovieService {
     Result insertMovie(Movie movie);
     Result selectMovieList(Movie movie,Boolean random, Integer already, Integer size, Integer page);
     Result updateMovie(Movie movie);
     Result selectSimpleMovieList();
     Result selectMovieListOrderByScore();
}
