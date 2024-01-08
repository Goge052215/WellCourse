package com.opensource.schoolforum.mapper;

import com.opensource.schoolforum.entity.Plug;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 插件表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
public interface PlugMapper extends BaseMapper<Plug> {


    @Select("select plug.id as id ,plug.`name` as `name`,plug.`code` as `code` from plug \n" +
            "INNER JOIN plugdiscipline on plug.id = plugdiscipline.plugid\n" +
            "WHERE plugdiscipline.discipineid =  #{disciplineId}")
    public List<Plug> queryPlugListFromDiscipline(Long disciplineId);

}
