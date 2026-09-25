package com.feidao.service.impl;

import com.feidao.exception.BizException;
import com.feidao.mapper.GoodsMapper;
import com.feidao.pojo.Goods;
import com.feidao.pojo.PageResult;
import com.feidao.service.GoodsService;
import com.feidao.utils.UserContext;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addGoods(Goods goods) {
        if(goods.getTitle() == null || goods.getTitle().isEmpty())
            throw new IllegalArgumentException("Title cannot be null or empty");
        if(goods.getPrice() <= 0)
            throw new IllegalArgumentException("Price must be greater than zero");
        Integer userId = UserContext.getCurrentUserId();
        goods.setUserId(userId);
        goodsMapper.insert(goods);
    }

    @Override
    public Goods getGoods(Integer id) {
        return goodsMapper.selectById(id);
    }

    @Override
    public void updateGoods(Goods goods) {
        Goods newgoods = goodsMapper.selectById(goods.getId());
        if (newgoods == null) {
            throw new BizException("Goods not found");
        }
        if (!Objects.equals(newgoods.getUserId(), UserContext.getCurrentUserId())){
            throw new BizException("You are not the owner of this goods");
        }
        goodsMapper.updateGoods(goods);
    }

    @Override
    public void deleteGoods(Integer id) {
        Goods goods = goodsMapper.selectById(id);
        if (goods == null) {
            throw new BizException("Goods not found");
        }
        if (!Objects.equals(goods.getUserId(), UserContext.getCurrentUserId())){
            throw new BizException("You are not the owner of this goods");
        }
        goodsMapper.deleteGoods(id);
    }

    @Override
    public PageResult<Goods> getGoodsByPage(Integer page, Integer size, String keyword) {
        PageHelper.startPage(page, size);
        List<Goods> goods = goodsMapper.selectByPage(keyword);
        PageInfo<Goods> pageInfo = new PageInfo<>(goods);
        return new PageResult<Goods>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public Goods checkGoodsExist(String goodsId, int status) {
        return goodsMapper.checkGoodsExist(goodsId, status);
    }
}
