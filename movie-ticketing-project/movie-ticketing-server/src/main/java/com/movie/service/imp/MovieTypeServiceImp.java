package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.mapper.MovieTypeMapper;
import com.movie.service.MovieTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieTypeServiceImp implements MovieTypeService {
    private final MovieTypeMapper movieTypeMapper;

    @Override
    public Result selectAllMovieType() {
        return Result.success(RespCode.FIND_SUCCESS).setData(
                movieTypeMapper.selectAllMovieType()
        );
    }
}
