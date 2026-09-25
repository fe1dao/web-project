package com.feidao.mapper;

import com.feidao.pojo.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {

    @Insert("INSERT INTO goods (title, description, price, cover, user_id) VALUES (#{title}, #{description}, #{price}, #{cover}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Goods goods);

    @Select("SELECT id, user_id, title, description, price, cover, status, create_time, update_time FROM goods WHERE id = #{id}")
    Goods selectById(Integer id);

    @Update("UPDATE goods SET title = #{title}, description = #{description}, price = #{price}, cover = #{cover} WHERE id = #{id}")
    void updateGoods(Goods goods);

    @Update("update goods set status = 2 WHERE id = #{id}")
    void deleteGoods(Integer id);

    @Select("SELECT id, user_id, title, description, price, cover, status, create_time, update_time FROM goods WHERE status = 1 " +
            "AND (title LIKE CONCAT('%',#{keyword},'%') OR description LIKE CONCAT('%',#{keyword},'%')) " +
            "ORDER BY create_time DESC")
    List<Goods> selectByPage(String keyword);

    @Select("SELECT id, user_id  FROM goods WHERE id = #{goodsId} AND status = #{status}")
    Goods checkGoodsExist(String goodsId, int status);

    @Update("UPDATE goods SET status = #{status0} WHERE id = #{id} AND status = #{status1}")
    int updateGoodsStatus(Integer id, int status0, int status1);
}
