package com.movie.service.imp;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.entity.ChildLayout;
import com.movie.entity.Order;
import com.movie.entity.code.OrderStatus;
import com.movie.exception.BusinessException;
import com.movie.mapper.OrderMapper;
import com.movie.mapper.SliceArrangementMapper;
import com.movie.service.OrderService;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    private final OrderMapper orderMapper;
    private final SliceArrangementMapper sliceArrangementMapper;

    @Override
    @Transactional
    public Result insertOrder(Order order) {
        // 查询用户是否还有未支付的订单，如果有就返回之前未支付的订单id
        Order orderAlready = orderMapper.selectOrderByUserIdNotPayOne(order.getUserId());
        if(orderAlready!=null){
           return Result.success(RespCode.ALREADY_ORDER_NOT_PAY,orderAlready.getOrderId());
        }
        // 将json格式的layout转为二维对象数组
        // 查询出对应排片的layout数据
        // 判断用户想要订购的座位是否已经被订购
        // 若未被订购，则插入订单数据，并更新对应排片的layout数据
        // 若已被订购，则返回错误信息，然后将页面刷新
        ChildLayout[][] customerLayout = JSON.parseObject(order.getLayout(), ChildLayout[][].class);
        ChildLayout[][] databaseLayout = JSON.parseObject(
                sliceArrangementMapper.selectLayoutBySliceArrangementId(order.getSliceArrangementId()),
                ChildLayout[][].class);
        for(int i=0;i<databaseLayout.length;i++){
            for(int j=0;j<databaseLayout[i].length;j++){
                // 如果是座位，并且已经被购买，用户选择了对应的座位，则返回错误信息
                // 如果是座位，并且没有被购买，用户选择了对应的座位，则修改购买的状态，后面重新封装为json格式的layout
                if(databaseLayout[i][j].getSeat()&&customerLayout[i][j].getIsSelected()){
                    if(databaseLayout[i][j].getIsBuy()){
                        throw new BusinessException(RespCode.SEAT_ALREADY_BUY);
                    }else{
                        customerLayout[i][j].setIsBuy(true);
                        customerLayout[i][j].setIsSelected(false);
                        customerLayout[i][j].setColor("red");
                    }
                }
            }
        }
        String newLayout=JSON.toJSONString(customerLayout, SerializerFeature.DisableCircularReferenceDetect);
        // 修改layout数据
        if(sliceArrangementMapper.updateSliceArrangementLayout(newLayout,order.getSliceArrangementId())>0){
            // 创建订单的id
            String orderId = UUIDUtils.generateUUID();
            order.setOrderId(orderId);
            if(orderMapper.insertOrder(order)>0){
                return Result.success(RespCode.INSET_SUCCESS,orderId);
            }else{
                throw new BusinessException(RespCode.INSET_ERROR);
            }
        }else{
            throw new BusinessException(RespCode.UPDATE_ERROR);
        }
    }

    @Override
    public Result selectOrderByOrderId(String orderId) {
        try {
            Order order = orderMapper.selectOrderByOrderId(orderId);
            return Result.success(RespCode.FIND_SUCCESS,order);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result updateOrderStatus(String orderId, Integer state) {
        try {
            if(orderMapper.updateOrderStatus(orderId,state)>0){
                if(state==OrderStatus.PAYMENT_CANCELLED.getCode()){
                    verifyOrderChild(List.of(orderMapper.selectOrderByOrderId(orderId)));
                }
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
    @Transactional
    public Result selectOrderBySliceArrangementId(String sliceArrangementId,Integer page,Integer size) {
        try{
            verifyOrder();
            PageHelper.startPage(page,size);
            List<Order> orders = orderMapper.selectOrderBySliceArrangementId(sliceArrangementId);
            return Result.success(RespCode.FIND_SUCCESS, PageInfo.of(orders));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public void verifyOrder() {
        // 查询支付超时并且没有修改状态的订单
        List<Order> orders = orderMapper.selectTimeOutOrderAndNotUpdateState();
        verifyOrderChild(orders,OrderStatus.PAYMENT_TIMEOUT);
    }
    public void verifyOrderChild(List<Order> orders, OrderStatus orderStatus){
        for(Order order:orders){
            if(sliceArrangementMapper.updateSliceArrangementLayout(
                    getNewLayout(order),order.getSliceArrangementId()
            )>0){
                // 修改订单的状态
                if(orderMapper.updateOrderStatus(order.getOrderId(),orderStatus.getCode())<=0){
                    throw new BusinessException(RespCode.UPDATE_ERROR);
                }
            }else{
                throw new BusinessException(RespCode.UPDATE_ERROR);
            }
        }
    }

    public void verifyOrderChild(List<Order> orders){
        for(Order order:orders){
            if(sliceArrangementMapper.updateSliceArrangementLayout(
                    getNewLayout(order),order.getSliceArrangementId()
            )<=0){
                throw new BusinessException(RespCode.UPDATE_ERROR);
            }
        }
    }
    public String getNewLayout(Order order){
        ChildLayout[] orderLayout= JSON.parseObject(order.getSeats(),ChildLayout[].class);
        // 首先要修改对应排片中的座位
        ChildLayout[][] sliceOldLayout = JSON.parseObject(sliceArrangementMapper.selectLayoutBySliceArrangementId(order.getSliceArrangementId()),ChildLayout[][].class);
        for (ChildLayout orderChild : orderLayout) {
            int targetX = orderChild.getX();
            int targetY = orderChild.getY();

            // 遍历 sliceOldLayout 二维数组
            for (ChildLayout[] row : sliceOldLayout) {
                for (ChildLayout oldChild : row) {
                    if (oldChild.getX() == targetX && oldChild.getY() == targetY) {
                        oldChild.setIsBuy(false);
                        oldChild.setColor("white");
                    }
                }
            }
        }
        // 将修改后的布局存入数据库
        return JSON.toJSONString(sliceOldLayout, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public Result selectOrderDetailByOrderId(String orderId) {
        try {
            Order orderInfo = orderMapper.selectOrderDetailByOrderId(orderId);
            return Result.success(RespCode.FIND_SUCCESS,orderInfo);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional
    public Result selectOrderDetailListByOrder(Order order, Integer page, Integer size) {
        try {
            verifyOrder();
            PageHelper.startPage(page,size);
            List<Order> orders = orderMapper.selectOrderDetailListByOrder(order);
            return Result.success(RespCode.FIND_SUCCESS,PageInfo.of(orders));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectOrderNumByTime(String cinemaId,LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectOrderNumByTime(cinemaId,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectOrderVoteAllPriceByTime(String cinemaId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectOrderVoteAllPriceByTime(cinemaId,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectAllOrderPrice(String cinemaId) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectAllOrderPrice(cinemaId));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectChartValueByTime(String cinemaId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectChartValueByTimeAndMovie(cinemaId,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectChartValueByTimeAndScreeningRoom(String cinemaId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectChartValueByTimeAndScreeningRoom(cinemaId,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result orderTotalAmount() {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.orderTotalAmount());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result payedOrderTotalAmount() {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.payedOrderTotalAmount());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result orderTotal() {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.orderTotal());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result payedOrderTotal() {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.payedOrderTotal());
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectOrderVoteAllPriceByTimeNotCinema(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectOrderVoteAllPriceByTimeNotCinema(startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectOrderVotePriceByTimeNotCinema(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectOrderVotePriceByTimeNotCinema(startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectChartValueByTimeAndMovieNotCinema(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectChartValueByTimeAndMovieNotCinema(startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }

    @Override
    public Result selectChartValueByTimeGroupCinema(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return Result.success(RespCode.FIND_SUCCESS,orderMapper.selectChartValueByTimeGroupCinema(startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RespCode.DATABASE_ERROR);
        }
    }
}
