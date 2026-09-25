package com.feidao.service;

import com.feidao.pojo.Goods;

import java.util.List;
import java.util.Map;

public interface OrderService {
    void createOrder(Goods goods);



    List<Map<String, Object>> myOrders();

    List<Map<String, Object>> soldOrders();

    void cancelOrder(Integer id);
}
