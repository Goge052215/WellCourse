package com.opensource.schoolforum.service;

import com.opensource.schoolforum.dto.CommentDTO;
import com.opensource.schoolforum.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
public interface CommentService extends IService<Comment> {


    void setChildren(CommentDTO commentDTO);

    List<CommentDTO> getAllComments(Long parentId);

}
