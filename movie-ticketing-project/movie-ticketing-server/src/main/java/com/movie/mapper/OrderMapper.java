package com.movie.mapper;

import com.movie.entity.ChartValue;
import com.movie.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    int insertOrder(@Param("order")Order order);
    Order selectOrderByUserIdNotPayOne(@Param("userId")String userId);
    Order selectOrderByOrderId(@Param("orderId")String orderId);
    int updateOrderStatus(@Param("orderId")String orderId,@Param("status")int status);
    List<Order> selectOrderBySliceArrangementId(@Param("sliceArrangementId")String sliceArrangementId);
    List<Order> selectTimeOutOrderAndNotUpdateState();
    Order selectOrderDetailByOrderId(@Param("orderId")String orderId);
    List<Order> selectOrderDetailListByOrder(@Param("order")Order order);
    int selectOrderNumByTime(
            @Param("cinemaId") String cinemaId,
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    double selectOrderVoteAllPriceByTime(
            @Param("cinemaId") String cinemaId,
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    double selectAllOrderPrice(@Param("cinemaId") String cinemaId);
    List<ChartValue> selectChartValueByTimeAndMovie(
            @Param("cinemaId") String cinemaId,
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    List<ChartValue> selectChartValueByTimeAndScreeningRoom(
            @Param("cinemaId") String cinemaId,
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    double orderTotalAmount();
    double payedOrderTotalAmount();
    double orderTotal();
    double payedOrderTotal();
    double selectOrderVoteAllPriceByTimeNotCinema(
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    double selectOrderVotePriceByTimeNotCinema(
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    List<ChartValue> selectChartValueByTimeAndMovieNotCinema(
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
    List<ChartValue> selectChartValueByTimeGroupCinema(
            @Param("startTime")LocalDateTime startTime,
            @Param("endTime")LocalDateTime endTime);
}
