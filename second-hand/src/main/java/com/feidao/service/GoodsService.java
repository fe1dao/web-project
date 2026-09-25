package com.feidao.service;

import com.feidao.pojo.Goods;
import com.feidao.pojo.PageResult;

public interface GoodsService {
    void addGoods(Goods goods);

    Goods getGoods(Integer id);

    void updateGoods(Goods goods);

    void deleteGoods(Integer id);

    PageResult<Goods> getGoodsByPage(Integer page, Integer size, String keyword);

    Goods checkGoodsExist(String goodsId,int status);
}
