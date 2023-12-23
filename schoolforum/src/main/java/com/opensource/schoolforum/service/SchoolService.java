package com.opensource.schoolforum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.entity.School;
import com.baomidou.mybatisplus.extension.service.IService;
import com.opensource.schoolforum.model.PagerModel;
import com.opensource.schoolforum.vo.SchoolDisciplinePageModel;

/**
 * <p>
 * 学校表 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
public interface SchoolService extends IService<School> {


    public Page<School> page(Page<School> page, QueryWrapper<School> queryWrapper );


    public PagerModel<School> pageList(Page<School> page, QueryWrapper<School> queryWrapper);

    public PagerModel<SchoolDisciplinePageModel> schoolDisciplinePageModelPage(Page<SchoolDisciplinePageModel> page, QueryWrapper<SchoolDisciplinePageModel> queryWrapper);
}
