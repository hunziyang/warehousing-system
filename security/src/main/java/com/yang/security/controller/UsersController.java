package com.yang.security.controller;

import com.yang.security.result.Result;
import com.yang.security.service.UserService;
import com.yang.security.vo.user.RegisterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;
    @PostMapping("/user/register")
    @ApiOperation("注册账号")
    public Result register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        return Result.success();
    }
}
