package com.feidao.mapper;

import com.feidao.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    @Select("SELECT id, status FROM t_order WHERE goods_id = #{goodsId} AND buyer_id = #{currentUserId} AND status = #{status}")
    Order checkOrder(Integer goodsId, Integer currentUserId, int status);

    @Insert("INSERT INTO t_order (goods_id, buyer_id, seller_id) VALUES (#{goodsId}, #{currentUserId}, #{sellerId})")
    void insertOrder(Order order);

    @Select("SELECT o.id, o.buyer_id, o.seller_id, o.status, g.title, g.price, g.status FROM t_order o join goods g on o.goods_id = g.id WHERE buyer_id = #{currentUserId}")
    List<Map<String, Object>> getBuyerOrders(Integer currentUserId);

    @Select("SELECT o.id, o.buyer_id, o.seller_id, o.status, g.title, g.price, g.status FROM t_order o join goods g on o.goods_id = g.id WHERE seller_id = #{currentUserId}")
    List<Map<String, Object>> getSellerOrders(Integer currentUserId);

    @Update("UPDATE t_order SET status = 2 WHERE id = #{id}")
    void cancelOrder(Integer id);

    @Select("SELECT id, goods_id, buyer_id, seller_id, status FROM t_order WHERE id = #{id} AND status = #{status}")
    Order getOrderByIdAndStatus(Integer id, int status);
}