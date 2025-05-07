package com.movie.service.imp;

import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.ScreeningRoom;
import com.movie.exception.BusinessException;
import com.movie.mapper.ScreeningRoomMapper;
import com.movie.service.ScreeningRoomService;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningRoomServiceImp implements ScreeningRoomService {
    private final ScreeningRoomMapper screeningRoomMapper;

    @Override
    public Result insertScreeningRoom(ScreeningRoom screeningRoom) {
        try{
            screeningRoom.setScreeningRoomId(UUIDUtils.generateUUID());
            if(screeningRoomMapper.insertScreeningRoom(screeningRoom)>0){
                return Result.success(RespCode.INSET_SUCCESS);
            }else{
                throw new BusinessException(RespCode.INSET_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectScreeningRoomByCinemaId(String cinemaId) {
        try {
            List<ScreeningRoom> screeningRooms = screeningRoomMapper.selectScreeningRoomByCinemaId(cinemaId);
            return Result.success(RespCode.FIND_SUCCESS, screeningRooms);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result updateScreeningRoom(ScreeningRoom screeningRoom) {
        try {
            if(screeningRoomMapper.updateScreeningRoom(screeningRoom)>0){
                return Result.success(RespCode.UPDATE_SUCCESS);
            }else{
                throw new BusinessException(RespCode.UPDATE_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectSimpleScreeningRoom(String cinemaId) {
        try {
            List<ScreeningRoom> screeningRooms = screeningRoomMapper.selectSimpleScreeningRoom(cinemaId);
            return Result.success(RespCode.FIND_SUCCESS, screeningRooms);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectScreeningRoomByScreeningRoomId(String screeningRoomId) {
        try {
            ScreeningRoom screeningRoom = screeningRoomMapper.selectScreeningRoomByScreeningRoomId(screeningRoomId);
            return Result.success(RespCode.FIND_SUCCESS, screeningRoom);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
