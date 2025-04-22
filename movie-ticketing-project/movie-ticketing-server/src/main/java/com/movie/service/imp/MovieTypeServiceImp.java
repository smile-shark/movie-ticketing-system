package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.MovieType;
import com.movie.exception.BusinessException;
import com.movie.mapper.MovieTypeMapper;
import com.movie.mapper.MovieTypeMiddleMapper;
import com.movie.service.MovieTypeService;
import com.movie.utils.UUIDUtils;
import com.movie.utils.VagueUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieTypeServiceImp implements MovieTypeService {
    private final MovieTypeMapper movieTypeMapper;
    private final MovieTypeMiddleMapper movieTypeMiddleMapper;

    @Override
    public Result selectAllMovieType() {
        return Result.success(RespCode.FIND_SUCCESS).setData(
                movieTypeMapper.selectAllMovieType()
        );
    }

    @Override
    public Result selectMovieTypesByName(String movieTypeName) {
        try {
            List<MovieType> movieTypes = movieTypeMapper.selectMovieTypesByName(VagueUtil.vague(movieTypeName));
            return Result.success(RespCode.FIND_SUCCESS).setData(movieTypes);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result insertMovieType(String movieTypeName) {
        try{
            if(movieTypeName==null ||movieTypeName.isEmpty()){
                return Result.error(RespCode.PARAM_ERROR);
            }
            if(movieTypeMapper.insertMovieType(new MovieType(){{
                setMovieTypeName(movieTypeName);
                setMovieTypeId(UUIDUtils.generateUUID());
            }})>0){
                return Result.success(RespCode.ADD_MOVIE_TYPE_SUCCESS);
            }else{
                return Result.error(RespCode.ADD_MOVIE_TYPE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result deleteMovieTypeById(String movieTypeId) {
        try {
            if(movieTypeId.length()!=32){
                return Result.error(RespCode.PARAM_ERROR);
            }
            // 首先要删除关联表中的
            movieTypeMiddleMapper.deleteMovieTypeMiddlesByMovieTypeId(movieTypeId);
            if(movieTypeMapper.deleteMovieTypeById(movieTypeId)>0){
                return Result.success(RespCode.DELETE_MOVIE_TYPE_SUCCESS);
            }else{
                return Result.error(RespCode.DELETE_MOVIE_TYPE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
