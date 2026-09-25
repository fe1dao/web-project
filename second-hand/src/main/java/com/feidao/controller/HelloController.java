package com.feidao.controller;

import com.feidao.exception.BizException;
import com.feidao.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public Result hello() {
        return Result.success("hello");
    }

    @GetMapping("/api/test/error")
    public Result testError() {
        throw  new BizException("测试业务异常");
    }
}
