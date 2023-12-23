package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.entity.Discipline;
import com.opensource.schoolforum.entity.School;
import com.opensource.schoolforum.model.DisciplinePageReq;
import com.opensource.schoolforum.model.PagerModel;
import com.opensource.schoolforum.model.R;
import com.opensource.schoolforum.model.SchoolDisciplinePageReq;
import com.opensource.schoolforum.service.DisciplineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 学科表 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/discipline")
@Api(value = "学科", tags = "学科")
@Slf4j
public class DisciplineController {


    @Autowired
    private DisciplineService disciplineService;

    /**
     * 学科分页列表
     * @return
     */
    @PostMapping("/listPage")
    @ApiOperation("学科分页列表listPage")
    public R<PagerModel<Discipline>> listPage(@Valid @RequestBody DisciplinePageReq disciplinePageReq) {
        // 条件构造器
        QueryWrapper<Discipline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schoolid", disciplinePageReq.getSchoolId());
        // 分页插件
        Page<Discipline> page = new Page(disciplinePageReq.getPageNo(), disciplinePageReq.getPageSize());
        // 查询数据
        return R.success(disciplineService.pageList(page, queryWrapper));
    }

}
