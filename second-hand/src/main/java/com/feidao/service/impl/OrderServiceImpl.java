package com.feidao.service.impl;

import com.feidao.mapper.GoodsMapper;
import com.feidao.mapper.OrderMapper;
import com.feidao.pojo.Goods;
import com.feidao.pojo.Order;
import com.feidao.service.OrderService;
import com.feidao.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    @Transactional
    public void createOrder(Goods goods) {

//        2.校验不能买自己的商品
        if (goods.getUserId().equals(UserContext.getCurrentUserId())) {
            throw new RuntimeException("不能买自己的商品");
        }
//        3.防止重复下单：同一商品同一买家不能有未完成订单
        Order order = orderMapper.checkOrder(goods.getId(), UserContext.getCurrentUserId(),0);
        if (order != null) {
            throw new RuntimeException("不能重复下单");
        }

//
//        4.防止超卖：更新商品状态时用条件更新
        int affectedRows = goodsMapper.updateGoodsStatus(goods.getId(), 1, 0);
//
//        5.判断影响行数，如果是 0，说明被别人抢先了，抛异常
        if (affectedRows == 0) {
            throw new RuntimeException("商品已售罄");
        }
//
//        6.生成订单记录
        Order neworder = new Order();
        neworder.setGoodsId(goods.getId());
        neworder.setBuyerId(UserContext.getCurrentUserId());
        neworder.setSellerId(goods.getUserId());
        orderMapper.insertOrder(neworder);
//
//        7.整个方法加 @Transactional
    }



    @Override
    public List<Map<String, Object>> myOrders() {
        return orderMapper.getBuyerOrders(UserContext.getCurrentUserId());
    }

    @Override
    public List<Map<String, Object>> soldOrders() {
        return orderMapper.getSellerOrders(UserContext.getCurrentUserId());
    }

    @Override
    @Transactional
    public void cancelOrder(Integer id) {
        Order order = orderMapper.getOrderByIdAndStatus(id, 0);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }
        orderMapper.cancelOrder(id);
        goodsMapper.updateGoodsStatus(order.getGoodsId(), 0, 1);
    }
}
