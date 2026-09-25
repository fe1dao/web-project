package com.feidao.mapper;

import com.feidao.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT id, username, password, nickname, avatar, create_time, update_time FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Insert("INSERT INTO user (username, password, nickname) VALUES (#{username}, #{password}, #{nickname})")
    void insert(User user);

    @Select("select id, username, nickname from user where username = #{username} and password = #{password}")
    User selectByUsernameAndPassword(String username, String password);

    @Select("SELECT id, username, nickname, avatar, create_time, update_time FROM user WHERE id = #{userId}")
    User selectById(Integer userId);
}
