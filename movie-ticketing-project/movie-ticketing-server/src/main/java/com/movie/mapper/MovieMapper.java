package com.movie.mapper;

import com.movie.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    int insertMovie(@Param("movie") Movie movie);
    /**
     * 查询电影列表。
     *
     * @param movie 查询条件对象，可以为 null，表示不进行特定条件筛选。
     *              当不为 null 时，可以根据 movie 对象中的属性进行筛选。
     * @param already 筛选条件，用于区分电影的上映状态：
     *                - null：获取全部电影；
     *                - 0：获取已上映的电影；
     *                - 1：获取未上映的电影。
     * @return 返回匹配查询条件的电影列表。
     */
    List<Movie> selectMovieList(@Param("movie") Movie movie,
                                @Param("already") Integer already
    );
    Movie selectMovieById(@Param("movieId") String movieId);
    int updateMovie(@Param("movie") Movie movie);
}
