package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.MovieType;

public interface MovieTypeService {
    Result selectAllMovieType();
    Result selectMovieTypesByName(String movieTypeName);
    Result insertMovieType(String movieTypeName );
    Result deleteMovieTypeById(String movieTypeId);
}
