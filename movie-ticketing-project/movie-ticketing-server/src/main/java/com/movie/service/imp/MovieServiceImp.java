package com.movie.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.Movie;
import com.movie.entity.MovieType;
import com.movie.entity.MovieTypeMiddle;
import com.movie.exception.BusinessException;
import com.movie.mapper.MovieMapper;
import com.movie.mapper.MovieTypeMapper;
import com.movie.mapper.MovieTypeMiddleMapper;
import com.movie.service.MovieService;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImp implements MovieService {
    private final MovieMapper movieMapper;
    private final MovieTypeMiddleMapper movieTypeMiddleMapper;
    private final MovieTypeMapper movieTypeMapper;

    @Override
    @Transactional
    public Result insertMovie(Movie movie) {
        // 先创建movieMapper中的movie
        String movieId = UUIDUtils.generateUUID();
        movie.setMovieId(movieId);
        if (movieMapper.insertMovie(movie) > 0) {
            // 再将类型分别插入到movieTypeMiddleMapper中
            for (MovieType tag : movie.getTags()) {
                if (movieTypeMiddleMapper.insertMovieTypeMiddle(
                        movieId, tag.getMovieTypeId()
                ) <= 0) {
                    throw new BusinessException(RespCode.ADD_MOVIE_ERROR);
                }
            }
        } else {
            throw new BusinessException(RespCode.ADD_MOVIE_ERROR);
        }
        return Result.success(RespCode.ADD_MOVIE_SUCCESS);
    }

    @Override
    public Result selectMovieList(Movie movie,Boolean random, Integer already, Integer size, Integer page) {
        try {
            Page<Object> startPage = PageHelper.startPage(page, size);
            List<Movie> movies = movieMapper.selectMovieList(movie, already,random);
            for (Movie movieItem : movies) {
                List<MovieTypeMiddle> movieTypeMiddles = movieTypeMiddleMapper.selectMovieTypeMiddlesByMovieId(
                        movieItem.getMovieId()
                );
                if(movieTypeMiddles.isEmpty()){
                    movieItem.setTags(new ArrayList<>());
                }else{
                    movieItem.setTags(
                            movieTypeMapper.selectMovieTypesInMovieTypeIds(
                                    movieTypeMiddles
                            )
                    );
                }
            }
            return Result.success(RespCode.FIND_SUCCESS,PageInfo.of(movies));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
    }

    @Override
    @Transactional
    public Result updateMovie(Movie movie) {
        try {
            // 1. 删除现有的类型标签F
            movieTypeMiddleMapper.deleteMovieTypeMiddlesByMovieId(movie.getMovieId());
            // 2. 插入新的类型标签
            for (MovieType tag : movie.getTags()) {
                if (movieTypeMiddleMapper.insertMovieTypeMiddle(
                        movie.getMovieId(), tag.getMovieTypeId()
                ) <= 0) {
                    throw new BusinessException(RespCode.UPDATE_MOVIE_ERROR);
                }
            }
            // 3. 更新movie
            if (movieMapper.updateMovie(movie) <= 0) {
                throw new BusinessException(RespCode.UPDATE_MOVIE_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(RespCode.UPDATE_MOVIE_ERROR);
        }
        return Result.success(RespCode.UPDATE_MOVIE_SUCCESS);
    }

    @Override
    public Result selectSimpleMovieList() {
        List<Movie> movies;
        try {
            movies= movieMapper.selectSimpleMovieList();
            return Result.success(RespCode.FIND_SUCCESS).setData(movies);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.FIND_ERROR);
        }
    }

    @Override
    public Result selectMovieListOrderByScore() {
        try {
            PageHelper.startPage(1,10);
            List<Movie> movies = movieMapper.selectMovieListOrderByScore();
            return Result.success(RespCode.UPDATE_SUCCESS,movies);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.UPDATE_ERROR);
        }
    }

    @Override
    public Result movieTotal() {
        try{
            return Result.success(RespCode.FIND_SUCCESS,movieMapper.movieTotal());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.UPDATE_ERROR);
        }
    }
}
