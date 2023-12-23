package com.opensource.schoolforum.service.impl;

import com.opensource.schoolforum.dto.CommentDTO;
import com.opensource.schoolforum.entity.Comment;
import com.opensource.schoolforum.mapper.CommentMapper;
import com.opensource.schoolforum.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper baseMapper;


    @Override
    public List<CommentDTO> getAllComments(Long parentId) {
        List<CommentDTO> rootComments = baseMapper.findByParentId(parentId);
        rootComments.forEach(this::setChildren);
        return rootComments;
    }

    /**
     * 递归获取
     * @param commentDTO 参数
     */
    @Override
    public void setChildren(CommentDTO commentDTO){
        List<CommentDTO> children = baseMapper.findByParentId(commentDTO.getId());
        if (!children.isEmpty()) {
            commentDTO.setChildren(children);
            children.forEach(this::setChildren);
        }
    }

}
