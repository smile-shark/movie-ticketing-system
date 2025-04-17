package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Movie;
import com.movie.entity.MovieType;
import com.movie.exception.BusinessException;
import com.movie.mapper.MovieMapper;
import com.movie.mapper.MovieTypeMiddleMapper;
import com.movie.service.MovieService;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MovieServiceImp implements MovieService {
     private final MovieMapper movieMapper;
     private final MovieTypeMiddleMapper movieTypeMiddleMapper;

     @Override
     @Transactional
     public Result insertMovie(Movie movie) {
          // 先创建movieMapper中的movie
          String movieId = UUIDUtils.generateUUID();
          movie.setMovieId(movieId);
          if(movieMapper.insertMovie(movie)>0){
               // 再将类型分别插入到movieTypeMiddleMapper中
               for (MovieType tag : movie.getTags()) {
                    if(movieTypeMiddleMapper.insertMovieTypeMiddle(
                            movieId,tag.getMovieTypeId()
                    )<=0){
                         throw new BusinessException(RespCode.ADD_MOVIE_ERROR);
                    };
               }
          }else{
               throw new BusinessException(RespCode.ADD_MOVIE_ERROR);
          }
          return Result.success(RespCode.ADD_MOVIE_SUCCESS);
     }
}
