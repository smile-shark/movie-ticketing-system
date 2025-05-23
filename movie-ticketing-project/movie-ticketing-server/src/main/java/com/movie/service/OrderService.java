package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Order;

import java.time.LocalDateTime;

public interface OrderService {
    Result insertOrder(Order order);
    Result selectOrderByOrderId(String orderId);
    Result updateOrderStatus(String orderId,Integer state,Integer paramState);
    Result selectOrderBySliceArrangementId(String sliceArrangementId,Integer page,Integer size);
    void verifyOrder();
    Result selectOrderDetailByOrderId(String orderId);
    Result selectOrderDetailListByOrder(Order order,Integer page,Integer size);
    Result selectOrderNumByTime(String cinemaId,LocalDateTime startTime,LocalDateTime endTime);
    Result selectOrderVoteAllPriceByTime(String cinemaId,LocalDateTime startTime,LocalDateTime endTime);
    Result selectAllOrderPrice(String cinemaId);
    Result selectChartValueByTime(String cinemaId,LocalDateTime startTime,LocalDateTime endTime);
    Result selectChartValueByTimeAndScreeningRoom(String cinemaId,LocalDateTime startTime,LocalDateTime endTime);
    Result orderTotalAmount();
    Result payedOrderTotalAmount();
    Result orderTotal();
    Result payedOrderTotal();
    Result selectOrderVoteAllPriceByTimeNotCinema(LocalDateTime startTime,LocalDateTime endTime);
    Result selectOrderVotePriceByTimeNotCinema(LocalDateTime startTime,LocalDateTime endTime);
    Result selectChartValueByTimeAndMovieNotCinema(LocalDateTime startTime,LocalDateTime endTime);
    Result selectChartValueByTimeGroupCinema(LocalDateTime startTime,LocalDateTime endTime);
    Result updateOrderStateByOrderIdAndState(String cinemaId,String orderId,Integer state,Integer paramState);
}
