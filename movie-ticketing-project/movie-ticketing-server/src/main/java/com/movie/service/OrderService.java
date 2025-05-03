package com.movie.service;

import com.movie.common.resp.Result;
import com.movie.entity.Order;

public interface OrderService {
    Result insertOrder(Order order);
    Result selectOrderByOrderId(String orderId);
    Result updateOrderStatus(String orderId,Integer state);
    Result selectOrderBySliceArrangementId(String sliceArrangementId,Integer page,Integer size);
    void verifyOrder();
    Result selectOrderDetailByOrderId(String orderId);
}
