package com.feidao.controller;

import com.feidao.pojo.Goods;
import com.feidao.pojo.Order;
import com.feidao.result.Result;
import com.feidao.service.GoodsService;
import com.feidao.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping
    public Result createOrder(@RequestParam String goodsId) {
        Goods goods = goodsService.checkGoodsExist(goodsId,0);
        if (goods == null){
            return Result.fail("商品不存在");
        }
        try {
            orderService.createOrder(goods);
            redisTemplate.delete("goods:detail:" + goods.getId());
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/my")
    public Result myOrders() {
        List<Map<String, Object>> orders = orderService.myOrders();
        return Result.success(orders);
    }

    @GetMapping("/sold")
    public Result soldOrders() {
        List<Map<String, Object>> orders = orderService.soldOrders();
        return Result.success(orders);
    }

    @PostMapping("/{id}/cancel")
    public Result cancelOrder(@PathVariable Integer id) {
        try {
            orderService.cancelOrder(id);
        } catch (Exception e) {
            log.error("取消订单失败", e);
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }
}
