package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Movie;

public interface MovieService {
     Result insertMovie(Movie movie);
}
