package com.movie.service.imp;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.SliceArrangement;
import com.movie.exception.BusinessException;
import com.movie.mapper.SliceArrangementMapper;
import com.movie.service.OrderService;
import com.movie.service.SliceArrangementService;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SliceArrangementServiceImp implements SliceArrangementService {
    private final SliceArrangementMapper sliceArrangementMapper;
    private final OrderService orderService;

    @Override
    @Transactional
    public Result insertSliceArrangement(SliceArrangement sliceArrangement) {
        try {
            // 先查看在这个时间段内这个放映厅是否有排片
            List<SliceArrangement> sliceArrangements = sliceArrangementMapper.selectSliceArrangementByScreeningRoomIdAndBetweenTime(
                    sliceArrangement.getScreeningRoomId(),
                    sliceArrangement.getSliceArrangementStartTime(),
                    sliceArrangement.getSliceArrangementEndTime()
            );
            // 若有排片，则不能插入
            if(!sliceArrangements.isEmpty()){
                throw new BusinessException(RespCode.HAVE_SLICE_ARRANGEMENT_IN_THIS_TIME);
            }
            // 创建排片id
            sliceArrangement.setSliceArrangementId(UUIDUtils.generateUUID());
            // layout由前端处理
            if (sliceArrangementMapper.insertSliceArrangement(sliceArrangement)>0){
                return Result.success(RespCode.INSET_SUCCESS);
            }else{
                throw new BusinessException(RespCode.INSET_ERROR);
            }
        }catch(BusinessException e){
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional
    public Result selectSliceArrangementBySliceArrangement(SliceArrangement sliceArrangement,Integer page,Integer size) {
        try {
            // 这里先验证是否有订单支付超时了，如果有需要先将座位状态修改回去
            orderService.verifyOrder();
            Page<Object> objects = PageHelper.startPage(page, size);
            List<SliceArrangement> sliceArrangements = sliceArrangementMapper.selectSliceArrangementBySliceArrangement(sliceArrangement);
            return Result.success(RespCode.FIND_SUCCESS, PageInfo.of(sliceArrangements));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectSliceArrangementNumByTime(String cinemaId, LocalDateTime startTime, LocalDateTime endTime) {
       try {
           return Result.success(RespCode.FIND_SUCCESS,sliceArrangementMapper.selectSliceArrangementNumByTime(cinemaId,startTime,endTime));
       }catch (Exception e){
           e.printStackTrace();
           throw new BusinessException(RespCode.DATABASE_ERROR);
       }
    }
}
