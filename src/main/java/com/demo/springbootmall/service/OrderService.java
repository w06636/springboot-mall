package com.demo.springbootmall.service;

import com.demo.springbootmall.dao.OrderQueryParams;
import com.demo.springbootmall.dto.CreateOrderRequest;
import com.demo.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
