package com.opensource.schoolforum.service.impl;

import com.opensource.schoolforum.entity.Post;
import com.opensource.schoolforum.mapper.PostMapper;
import com.opensource.schoolforum.service.PostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 帖子 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
