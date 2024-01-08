package com.opensource.schoolforum.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.entity.Discipline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.opensource.schoolforum.entity.School;
import com.opensource.schoolforum.model.PagerModel;

/**
 * <p>
 * 学科表 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
public interface DisciplineService extends IService<Discipline> {



    public PagerModel<Discipline> pageList(Page<Discipline> page, QueryWrapper<Discipline> queryWrapper);


}
