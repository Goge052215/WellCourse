package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.constant.PromptConstant;
import com.opensource.schoolforum.dto.CommentDTO;
import com.opensource.schoolforum.entity.*;
import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.model.*;
import com.opensource.schoolforum.service.CommentService;
import com.opensource.schoolforum.service.MessageService;
import com.opensource.schoolforum.service.PostService;
import com.opensource.schoolforum.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/comment")
@Api(value = "评论", tags = "评论")
@Slf4j
public class CommentController {


    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private PostService postService;


    @Autowired
    private RedissonClient redissonClient;

    @PostMapping("addComment")
    @ApiOperation("添加评论")
    @LoginToken
    @Transactional
    public R<Comment> addComment(@RequestBody @Valid AddCommentReq addCommentReq){
        Comment comment = new Comment();
        comment.setContent(addCommentReq.getContent());
        comment.setParentid(addCommentReq.getParentId());
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        //存数据
        comment.setUsername(user.getUsername());
        comment.setUserid(user.getId());
        comment.setContent(addCommentReq.getContent());
        comment.setPostid(addCommentReq.getPostId());
        comment.setParentid(addCommentReq.getParentId());
        comment.setHeadsculpture(user.getHeadsculpture());
        commentService.save(comment);
        //发消息
        Post post = postService.getById(addCommentReq.getPostId());
        if(post == null){
            return R.failure(ResultCode.FAILURE, PromptConstant.NO_DATA);
        }
        //发送消息
        if(user.getId().longValue()!= post.getUserid().longValue()) {
            Message message = new Message();
            message.setReadstatus(0);
            message.setType(3);
            message.setReceiveUserid(post.getUserid());
            message.setSendUserid(user.getId());
            message.setResourcesid(addCommentReq.getPostId());
            message.setContent(addCommentReq.getContent());
            messageService.save(message);
        }
        //同步es
        RLock lock = redissonClient.getLock("comment"+String.valueOf(addCommentReq.getPostId()));
        try {
            lock.lock();
            updateSum(1,addCommentReq.getPostId());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            // 释放锁
            lock.unlock();
        }
        LocalDateTime date =  LocalDateTime.now();
        comment.setCreateTime(date);
        comment.setUpdateTime(date);
        return R.success(comment);
    }

    @PostMapping("delComment")
    @ApiOperation("删除评论")
    @LoginToken
    public R<?> delComment(@RequestBody @Valid DelCommentReq delCommentReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        Comment comment = commentService.getById(delCommentReq.getCommentId());
        if(comment == null){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        if(user.getId() != comment.getUserid()){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_PERMISSION_NO_DATA);
        }
        commentService.removeById(delCommentReq.getCommentId());
        //同步es
        RLock lock = redissonClient.getLock("comment"+String.valueOf(comment.getParentid()));
        try {
            lock.lock();
            updateSum(2,comment.getParentid());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            // 释放锁
            lock.unlock();
        }
        return R.success();
    }
    /**
     * @param operate 1.添加 2取消
     * @param postId 帖子id
     */
    public void updateSum(int operate ,Long postId ){
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        query.withQuery(QueryBuilders.matchQuery("postId", postId));
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()>0){
            postes = searchHitList.get(0).getContent();
        }else {
            return;
        }
        //评论数量
        long commentNum = postes.getCommentNum();
        Document document = Document.create();
        if(operate == 1){
                document.put("commentNum", commentNum+1);
        }else{
                document.put("commentNum", commentNum-1);
        }
        elasticsearchRestTemplate.update(UpdateQuery.builder(postes.getId()).withDocument(document).build(), IndexCoordinates.of(Postes.INDEXNAME));
    }



    @PostMapping("firstComment")
    @ApiOperation("第一级评论")
    public R<PagerModel<Comment>> firstComment(@RequestBody @Valid FirstCommentReq firstCommentReq){
        // 条件构造器
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentid",0);
        queryWrapper.eq("postid",firstCommentReq.getPostId());
        // 分页插件
        Page<Comment> page = new Page(firstCommentReq.getPageNo(), firstCommentReq.getPageSize());
        // 查询数据
        Page<Comment> pageComment = commentService.page(page,queryWrapper);
        return R.success(new PagerModel<>(pageComment.getTotal(), pageComment.getRecords(),page.getPages(),page.getCurrent()));
    }

    @PostMapping("secondComment")
    @ApiOperation("第二级评论")
    public R<List<CommentDTO> > secondComment(@RequestBody @Valid SecondCommentReq secondCommentReq){
        List<CommentDTO> commentDTOList = commentService.getAllComments(secondCommentReq.getFirstCommentId());
        return R.success(commentDTOList);
    }

}
