package com.opensource.schoolforum.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.dto.CommentDTO;
import com.opensource.schoolforum.dto.MessageDto;
import com.opensource.schoolforum.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.opensource.schoolforum.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 消息 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-10-28
 */
public interface MessageMapper extends BaseMapper<Message> {

 /*   @Select("select user.username as username,user.headsculpture as headsculpture,user.id as userId, post.id as postId,post.title as postTitle,message.type as messageType,message.create_time as createTime from  message\n" +
            "INNER JOIN post on message.resourcesid = post.id\n" +
            "INNER JOIN user on user.id = post.userid\n" +
            "where message.userid = #{userId}")*/
    @Select("select message.content as content,message.id as id,message.readstatus as readstatus, user.username as username,user.headsculpture as headsculpture,user.id as userId, post.id as postId,post.title as postTitle,message.type as messageType,message.create_time as createTime from  message\n" +
            "INNER JOIN post on message.resourcesid = post.id\n" +
            "INNER JOIN user on user.id = message.senduserid\n" +
            "${ew.customSqlSegment} order by message.create_time desc")
    Page<MessageDto>  queryList(Page<MessageDto> page , @Param(Constants.WRAPPER) LambdaQueryWrapper<MessageDto> lambda, @Param("userId") Long userId);

}
