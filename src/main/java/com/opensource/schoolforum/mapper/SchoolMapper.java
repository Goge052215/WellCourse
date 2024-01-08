package com.opensource.schoolforum.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.entity.School;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.opensource.schoolforum.vo.SchoolDisciplinePageModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学校表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
public interface SchoolMapper extends BaseMapper<School> {


    Page<SchoolDisciplinePageModel> pageQuery(@Param("page") Page<?> page, @Param(Constants.WRAPPER) Wrapper<?> wrapper);

}
