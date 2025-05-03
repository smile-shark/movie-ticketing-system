package com.movie.mapper;

import com.movie.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
