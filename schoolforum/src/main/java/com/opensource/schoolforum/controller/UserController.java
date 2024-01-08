package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.constant.PromptConstant;
import com.opensource.schoolforum.entity.Emailcode;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.enums.EmailCodeType;
import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.model.*;
import com.opensource.schoolforum.service.EmailService;
import com.opensource.schoolforum.service.EmailcodeService;
import com.opensource.schoolforum.service.UserService;
import com.opensource.schoolforum.utils.EncryptBasedDesUtil;
import com.opensource.schoolforum.vo.SchoolDisciplinePageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.beans.Transient;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户", tags = "用户")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailcodeService emailcodeService;


    @PostMapping("/sendEmailCode")
    @ApiOperation("发送邮箱验证码")
    public R<?> sendEmailCode(@RequestBody @Valid SendEmailCodeReq sendEmailCodeReq){
        String code = generateRandomNumber();
        emailService.sendSimpleMail(sendEmailCodeReq.getEmail(),"Code",code);
        Emailcode emailcode = new Emailcode();
        emailcode.setCode(code);
        emailcode.setType(sendEmailCodeReq.getType());
        emailcode.setEmail(sendEmailCodeReq.getEmail());
        emailcodeService.save(emailcode);
        return R.success();
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    @Transactional
    public R<?> register(@RequestBody @Valid RegisterReq registerReq){
        //判断验证码
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        queryWrapper.eq("type", EmailCodeType.REGISTER.getIndex());
        queryWrapper.eq("email",registerReq.getUserEmail());
        queryWrapper.eq("code",registerReq.getCode());
        Emailcode emailcode = emailcodeService.getOne(queryWrapper);
        if(emailcode ==null) {
            return R.failure(ResultCode.PARAMETERERROR, "Verification code error！");
        }
        //判断邮箱是否注册
        QueryWrapper queryWrapperUser = new QueryWrapper();
        queryWrapperUser.eq("useremail", registerReq.getUserEmail());
        User userOld = userService.getOne(queryWrapperUser);
        if(userOld !=null){
            return R.failure(ResultCode.FAILURE,"Email already registered");
        }
        //添加用户
        User user = new User();
        user.setUseremail(registerReq.getUserEmail());
        user.setUsername(registerReq.getUserName());
        user.setPassword(EncryptBasedDesUtil.encryptBasedDes(registerReq.getPassWord()));
        user.setHeadsculpture(registerReq.getHeadSculpture());
        userService.save(user);
        //删除验证码
        emailcodeService.removeById(emailcode.getId());
        return R.success();
    }

    @PostMapping("/updateUserInfo")
    @ApiOperation("修改用户信息")
    @LoginToken
    public R<?> updateUserInfo(@RequestBody @Valid UpdateUserInfoReq updateUserInfoReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        user.setHeadsculpture(updateUserInfoReq.getHeadSculpture());
        user.setUsername(updateUserInfoReq.getUserName());
        userService.updateById(user);
        return R.success();
    }

    @PostMapping("/userInfo")
    @ApiOperation("查询用户信息")
    @LoginToken
    public R<User> userInfo(){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        user.setPassword(null);
        return R.success(user);
    }


    @PostMapping("/userInfoByEmail")
    @ApiOperation("查询用户信息")
    @LoginToken
    public R<User> userInfoByEmail(@RequestBody @Valid UserInfoByEmailReq userInfoByEmailReq){
        User user = userService.getUser(userInfoByEmailReq.getUserEmail());
        user.setPassword(null);
        return R.success(user);
    }

    @PostMapping("/updateUserPassword")
    @ApiOperation("修改用户密码")
    @LoginToken
    public R<?> updateUserPassword(@RequestBody @Valid UpdateUserPasswordReq  updateUserPasswordReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        user.setPassword(EncryptBasedDesUtil.encryptBasedDes(updateUserPasswordReq.getPassWord()));
        userService.updateById(user);
        return R.success();
    }

    @PostMapping("/retrievePassword")
    @ApiOperation("找回密码")
    @Transactional
    public R<?> retrievePassword(@RequestBody @Valid RetrievePasswordReq retrievePasswordReq){
        User user = userService.getUser(retrievePasswordReq.getUserEmail());
        if(user == null){
            return R.failure(ResultCode.FAILURE,"user does not exist");
        }
        //判断验证码
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 1");
        queryWrapper.eq("type", EmailCodeType.RETRIEVEPASSWORD.getIndex());
        queryWrapper.eq("email",retrievePasswordReq.getUserEmail());
        queryWrapper.eq("code",retrievePasswordReq.getCode());
        Emailcode emailcode = emailcodeService.getOne(queryWrapper);
        if(emailcode ==null) {
            return R.failure(ResultCode.PARAMETERERROR, "Verification code error！");
        }
        //修改用户密码
        user.setPassword(EncryptBasedDesUtil.encryptBasedDes(retrievePasswordReq.getPassWord()));
        userService.updateById(user);
        //删除验证码
        emailcodeService.removeById(emailcode.getId());
        return R.success();
    }


    /**
     * 用户列表 ,分页
     * @return
     */
    @PostMapping("/userPage")
    @ApiOperation("用户列表")
    @LoginToken
    public R<PagerModel<User>> userPage(@RequestBody @Valid UserPageReq userPageReq){
        //判断是否是管理员
        String userEmail =  BaseUserInfo.getUserId();
        User userRole = userService.getUser(userEmail);
        if(2==userRole.getRole()){
            return R.failure(ResultCode.ERROR, PromptConstant.NO_PERMISSION);
        }
         // 条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(null != userPageReq.getRole()){
            queryWrapper.eq("role",userPageReq.getRole());
        }
        // 分页插件
        Page<User> page = new Page(userPageReq.getPageNo(), userPageReq.getPageSize());
        // 查询数据
        Page<User> pageUser = userService.page(page,queryWrapper);
        // 获取当前页数据集合
        List<User> records = pageUser.getRecords();
        if(!records.isEmpty()){
            records.stream().forEach(user -> user.setPassword(null));
        }
        return R.success(new PagerModel<>(pageUser.getTotal(), records,page.getPages(),page.getCurrent()));
    }

    /**
     * 修改用户状态
     * @return
     */
    @PostMapping("/updateUserStatus")
    @ApiOperation("修改用户状态")
    @LoginToken
    public R<?> updateUserStatus(@RequestBody @Valid UpdateUserStatusReq updateUserStatusReq){
        //判断是否是管理员
        String userEmail =  BaseUserInfo.getUserId();
        User userRole = userService.getUser(userEmail);
        if(2==userRole.getRole()){
            return R.failure(ResultCode.ERROR,PromptConstant.NO_PERMISSION);
        }
        User user = new User();
        user.setId(updateUserStatusReq.getUserId());
        user.setStatus(updateUserStatusReq.getStatus());
        userService.updateById(user);
        return R.success();
    }


    public static String generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

}
