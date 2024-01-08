package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.entity.School;
import com.opensource.schoolforum.model.PagerModel;
import com.opensource.schoolforum.model.R;
import com.opensource.schoolforum.model.SchoolDisciplinePageReq;
import com.opensource.schoolforum.service.SchoolService;
import com.opensource.schoolforum.vo.SchoolDisciplinePageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 学校表 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/school")
@Api(value = "学校", tags = "学校")
@Slf4j
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * 分页列表
     * @param pageNum
     * @param pageSize
     * @return

    @GetMapping("/page")
    @ApiOperation("学校分页列表")*/
    public Page<School> page(/*@RequestParam(value = "keyword", defaultValue = "") String keyword,*/
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        // 条件构造器
        QueryWrapper<School> queryWrapper = new QueryWrapper<>();
        // 模糊查询Like
        //queryWrapper.like("nickname", keyword);
        // 分页插件
        Page<School> page = new Page(pageNum, pageSize);
        // 查询数据
        return schoolService.page(page,queryWrapper);
    }

    /**
     * 学校分页列表
     * @return
     */
    @PostMapping("/listPage")
    @ApiOperation("学校分页列表listPage")
    public R<PagerModel<School>> listPage(@RequestBody @Valid SchoolDisciplinePageReq schoolDisciplinePageReq) {
        // 条件构造器
        QueryWrapper<School> queryWrapper = new QueryWrapper<>();
        // 分页插件
        Page<School> page = new Page(schoolDisciplinePageReq.getPageNo(), schoolDisciplinePageReq.getPageSize());
        // 查询数据
        return R.success(schoolService.pageList(page, queryWrapper));
    }


    /**
     * 学校科目 ,分页列表
     * @return
     */
    @PostMapping("/schoolDisciplinePage")
    @ApiOperation("学校科目")
    public R<PagerModel<SchoolDisciplinePageModel>> schoolDisciplinePage(@RequestBody @Valid SchoolDisciplinePageReq schoolDisciplinePageReq) {
        // 条件构造器
        QueryWrapper<SchoolDisciplinePageModel> queryWrapper = new QueryWrapper<>();
        // 模糊查询Like
        //queryWrapper.like("nickname", keyword);
        // 分页插件
        Page<SchoolDisciplinePageModel> page = new Page(schoolDisciplinePageReq.getPageNo(), schoolDisciplinePageReq.getPageSize());
        // 查询数据
        return R.success(schoolService.schoolDisciplinePageModelPage(page, queryWrapper));
    }

}
