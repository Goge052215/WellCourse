package com.opensource.schoolforum.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.opensource.schoolforum.dto.CommentDTO;
import com.opensource.schoolforum.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
public interface CommentMapper extends BaseMapper<Comment> {


    @Select("SELECT id, userid, headsculpture,username, content, parentid,postid ,create_time as createTime,update_time as updateTime FROM comment WHERE parentid = #{parentId}")
    List<CommentDTO> findByParentId(Long parentId);
}
