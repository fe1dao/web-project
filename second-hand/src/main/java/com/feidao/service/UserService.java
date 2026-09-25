package com.feidao.service;

import com.feidao.pojo.LoginInfo;
import com.feidao.pojo.User;

public interface UserService {
    void register(User user);

    LoginInfo login(String username, String password);

    User getMe();
}
