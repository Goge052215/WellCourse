package com.opensource.schoolforum.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.opensource.schoolforum.annotate.PassToken;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.enums.EmailCodeType;
import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.service.TokenService;
import com.opensource.schoolforum.service.UserService;
import com.opensource.schoolforum.model.R;
import com.opensource.schoolforum.model.LoginUserReq;
import com.opensource.schoolforum.utils.EncryptBasedDesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "登陆", tags = "登陆")
@Slf4j
public class LoginController {


    @Resource
    private UserService userService;

    @Resource
    private TokenService tokenService;


    @PostMapping("/login")
    @ApiOperation("登陆-成功返回token")
    @PassToken
    public R<?> login(@RequestBody @Valid LoginUserReq loginUserReq){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("useremail", loginUserReq.getEmail());
        queryWrapper.eq("password", EncryptBasedDesUtil.encryptBasedDes(loginUserReq.getPassword()));
        User user = userService.getOne(queryWrapper);
        if(user == null){
            return R.failure(ResultCode.FAILURE,"Incorrect email or password!");
        }
        if(2==user.getStatus()){
            return R.failure(ResultCode.FAILURE,"Account Disabled Contact Administrator!");
        }
        return R.success(tokenService.getToken(user));
    }

}