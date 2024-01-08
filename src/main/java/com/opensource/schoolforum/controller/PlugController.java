package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.dto.MessageDto;
import com.opensource.schoolforum.entity.Plug;
import com.opensource.schoolforum.entity.School;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.model.*;
import com.opensource.schoolforum.service.PlugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 插件表 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/plug")
@Api(value = "插件", tags = "插件")
@Slf4j
public class PlugController {

    @Autowired
    private PlugService plugService;

    @PostMapping("/firstPlugList")
    @ApiOperation("第一级插件")
    @LoginToken
    public R<List<Plug>> firstPlugList(@RequestBody @Valid FirstPlugListReq firstPlugListReq){
        List<Plug> plugs  = plugService.queryPlugListFromDiscipline(firstPlugListReq.getDiscipineId());
        return R.success(plugs);
    }


    @PostMapping("/secondPlugList")
    @ApiOperation("第二级插件")
    @LoginToken
    public R<List<Plug>> secondPlugList(@RequestBody @Valid SecondPlugListReq secondPlugListReq){
        // 条件构造器
        QueryWrapper<Plug> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentid",secondPlugListReq.getParentId());
        List<Plug> plugs  = plugService.list(queryWrapper);
        return R.success(plugs);
    }


    @PostMapping("/plugInfo")
    @ApiOperation("查询插件详情")
    @LoginToken
    public R<Plug> plugInfo(@RequestBody @Valid PlugInfoReq plugInfoReq){
        // 条件构造器
        Plug plugs  = plugService.getById(plugInfoReq.getId());
        return R.success(plugs);
    }

}
