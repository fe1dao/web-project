package com.feidao.service.impl;

import com.feidao.exception.BizException;
import com.feidao.mapper.UserMapper;
import com.feidao.pojo.LoginInfo;
import com.feidao.pojo.User;
import com.feidao.service.UserService;
import com.feidao.utils.MyJwt;
import com.feidao.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional
    public void register(User user) {
        if(user.getUsername() == null || user.getPassword() == null || user.getNickname() == null){
            throw new IllegalArgumentException("Username, password, and nickname cannot be null");
        }
        if(userMapper.selectByUsername(user.getUsername()) != null){
            throw new BizException("Username already exists");
        }
        userMapper.insert(user);
    }

    @Override
    public LoginInfo login(String username, String password) {
        User user=userMapper.selectByUsernameAndPassword(username, password);
        if(user!=null){
            Map<String, Object> map =new HashMap<>();
            map.put("userId", user.getId());
            String token = MyJwt.generateToken(map);
            return new LoginInfo(user.getId(), user.getUsername(), user.getNickname(), token);
        }
        return null;
    }

    @Override
    public User getMe() {
        Integer userId = UserContext.getCurrentUserId();
        return userMapper.selectById(userId);
    }
}
