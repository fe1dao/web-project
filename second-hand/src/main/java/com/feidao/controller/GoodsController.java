package com.feidao.controller;

import com.feidao.pojo.Goods;
import com.feidao.pojo.PageResult;
import com.feidao.result.Result;
import com.feidao.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping
    public Result addGoods(@RequestBody Goods goods) {
        try {
            goodsService.addGoods(goods);
        } catch (Exception e) {
            log.error("Error adding goods: {}", e.getMessage());
            return Result.fail(e.getMessage());
        }
        return Result.success(goods.getId());
    }

    @GetMapping("/{id}")
    public Result getGoods(@PathVariable Integer id) {
        Goods goods = null;
        if(redisTemplate.hasKey("goods:detail:" + id)){
            goods = (Goods) redisTemplate.opsForValue().get("goods:detail:" + id);
            return Result.success(goods);
        }
        try {
            goods = goodsService.getGoods(id);
            if (goods != null) {
                redisTemplate.opsForValue().set("goods:detail:" + id, goods, 30, TimeUnit.MINUTES);
            } else {
                // 可选：缓存空值防穿透，比如 60 秒
                redisTemplate.opsForValue().set("goods:detail:" + id, "", 60, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            log.error("Error getting goods: {}", e.getMessage());
            return Result.fail(e.getMessage());
        }
        return Result.success(goods);
    }

    @PutMapping
    public Result updateGoods(@RequestBody Goods goods) {
        try {
            goodsService.updateGoods(goods);
            redisTemplate.delete("goods:detail:" + goods.getId());
        } catch (Exception e) {
            log.error("Error updating goods: {}", e.getMessage());
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteGoods(@PathVariable Integer id) {
        try {
            goodsService.deleteGoods(id);
            redisTemplate.delete("goods:detail:" + id);
        } catch (Exception e) {
            log.error("Error deleting goods: {}", e.getMessage());
            return Result.fail(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/page")
    public Result getGoodsByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false, defaultValue = "") String keyword) {
        PageResult<Goods> goods;
        try {
            goods = goodsService.getGoodsByPage(page, size, keyword);
        } catch (Exception e) {
            log.error("Error getting goods by page: {}", e.getMessage());
            return Result.fail(e.getMessage());
        }
        return Result.success(goods);
    }
}
