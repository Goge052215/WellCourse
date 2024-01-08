package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.constant.PromptConstant;
import com.opensource.schoolforum.entity.Sensitive;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.model.*;
import com.opensource.schoolforum.service.SensitiveService;
import com.opensource.schoolforum.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-11-25
 */
@RestController
@RequestMapping("/sensitive")
@Api(value = "敏感词", tags = "敏感词")
public class SensitiveController {


    @Autowired
    private SensitiveService sensitiveService;

    @Autowired
    private UserService userService;

    @PostMapping("/addSensitive")
    @ApiOperation("添加敏感词")
    @LoginToken
    public R<?> addSensitive(@RequestBody @Valid AddSensitiveReq addSensitiveReq){
        //判断是否是管理员
        String userEmail =  BaseUserInfo.getUserId();
        User userRole = userService.getUser(userEmail);
        if(2==userRole.getRole()){
            return R.failure(ResultCode.ERROR, PromptConstant.NO_PERMISSION);
        }
        //查询敏感词是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("word",addSensitiveReq.getWord());
        List<Sensitive> sensitiveList = sensitiveService.list(queryWrapper);
        if(sensitiveList !=null && sensitiveList.size()>0){
            return  R.failure(ResultCode.FAILURE,"Word presence");
        }
        Sensitive sensitive = new Sensitive();
        sensitive.setWord(addSensitiveReq.getWord());
        sensitiveService.save(sensitive);
        return R.success();
    }

    @PostMapping("/deleteSensitive")
    @ApiOperation("添加敏感词")
    @LoginToken
    public R<?> deleteSensitive(@RequestBody @Valid DeleteSensitiveReq deleteSensitiveReq) {
        //判断是否是管理员
        String userEmail = BaseUserInfo.getUserId();
        User userRole = userService.getUser(userEmail);
        if (2 == userRole.getRole()) {
            return R.failure(ResultCode.ERROR, PromptConstant.NO_PERMISSION);
        }
        sensitiveService.removeById(deleteSensitiveReq.getId());
        return R.success();
    }



    @PostMapping("/pageSensitive")
    @ApiOperation("敏感词分页")
    @LoginToken
    public R<PagerModel<Sensitive>> pageSensitive(@RequestBody @Valid PageSensitiveReq pageSensitiveReq) {
        //判断是否是管理员
        String userEmail = BaseUserInfo.getUserId();
        User userRole = userService.getUser(userEmail);
        if (2 == userRole.getRole()) {
            return R.failure(ResultCode.ERROR, PromptConstant.NO_PERMISSION);
        }
        // 条件构造器
        QueryWrapper<Sensitive> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("word",pageSensitiveReq.getWord());
        // 分页插件
        Page<Sensitive> page = new Page(pageSensitiveReq.getPageNo(), pageSensitiveReq.getPageSize());
        // 查询数据
        Page<Sensitive> sensitivePage = sensitiveService.page(page, queryWrapper);
        // 获取当前页数据集合
        List<Sensitive> records = sensitivePage.getRecords();
        return R.success(new PagerModel<>(sensitivePage.getTotal(), records, page.getPages(), page.getCurrent()));
    }

}
