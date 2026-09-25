package com.feidao.controller;

import com.feidao.pojo.LoginInfo;
import com.feidao.pojo.User;
import com.feidao.result.Result;
import com.feidao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            log.error("注册失败", e);
            return Result.fail(e.getMessage());
        }
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result login(String username, String password) {
        LoginInfo loginInfo =userService.login(username, password);
        if (loginInfo == null){
            log.error("登录失败");
            return Result.fail("登录失败");
        }
        return Result.success(loginInfo);
    }

    @GetMapping("/me")
    public Result me() {
        User user = userService.getMe();
        return Result.success();
    }
}
