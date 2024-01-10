package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.annotate.PassToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.constant.PromptConstant;
import com.opensource.schoolforum.entity.*;
import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.model.CancellationReq;
import com.opensource.schoolforum.model.IncreaseReq;
import com.opensource.schoolforum.model.R;
import com.opensource.schoolforum.service.LikecollectionService;
import com.opensource.schoolforum.service.MessageService;
import com.opensource.schoolforum.service.PostService;
import com.opensource.schoolforum.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 点赞收藏 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@RestController
@RequestMapping("/likecollection")
@Api(value = "点赞收藏", tags = "点赞收藏")
@Slf4j
public class LikecollectionController {

    @Autowired
    private LikecollectionService likecollectionService;

    @Autowired
    private UserService userService;


    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private PostService postService;


    @Autowired
    private MessageService messageService;

    @Autowired
    private RedissonClient redissonClient;


    @PostMapping("increase")
    @ApiOperation("添加点赞收藏")
    @LoginToken
    @Transactional
    public R<?> increase(@RequestBody @Valid IncreaseReq increaseReq){
        Post post = postService.getById(increaseReq.getPostId());
        if(post == null){
            return R.failure(ResultCode.FAILURE, PromptConstant.NO_DATA);
        }
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        RLock lock = redissonClient.getLock(String.valueOf(user.getId())+String.valueOf(increaseReq.getPostId()));
        try {
            lock.lock();
            QueryWrapper<Likecollection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("postid", increaseReq.getPostId());
            queryWrapper.eq("type", increaseReq.getType());
            queryWrapper.eq("userid", user.getId());
            List<Likecollection> likecollectionList = likecollectionService.list(queryWrapper);
            if (likecollectionList != null && likecollectionList.size() > 0) {
                if (1 == increaseReq.getType()) {
                    return R.failure(ResultCode.FAILURE, "Liked");
                } else {
                    return R.failure(ResultCode.FAILURE, "Favorite");
                }

            }
            //保存数据
            Likecollection likecollection = new Likecollection();
            likecollection.setPostid(increaseReq.getPostId());
            likecollection.setType(increaseReq.getType());
            likecollection.setUserId(post.getUserid());
            likecollectionService.save(likecollection);
            //发送消息
            if (user.getId().longValue() != post.getUserid().longValue()) {
                Message message = new Message();
                message.setReadstatus(0);
                message.setType(increaseReq.getType());
                message.setReceiveUserid(post.getUserid());
                message.setSendUserid(user.getId());
                message.setResourcesid(post.getId());
                if (1 == increaseReq.getType()) {
                    message.setContent("Someone likes your post");
                } else {
                    message.setContent("Someone has already collected your post");
                }
                messageService.save(message);
            }
            //同步es
            updateSum(1, increaseReq.getType(), increaseReq.getPostId());
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            // 释放锁
            lock.unlock();
        }
        return R.success();
    }


    @PostMapping("cancellation")
    @ApiOperation("取消点赞收藏")
    @LoginToken
    @Transactional
    public R<?>  cancellation(@RequestBody @Valid CancellationReq cancellationReq){
        Post post = postService.getById(cancellationReq.getPostId());
        if(post == null){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        RLock lock = redissonClient.getLock(String.valueOf(user.getId())+String.valueOf(cancellationReq.getPostId()));
        try {
            lock.lock();
            QueryWrapper<Likecollection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("postid",cancellationReq.getPostId());
            queryWrapper.eq("type",cancellationReq.getType());
            queryWrapper.eq("userid",user.getId());
            List<Likecollection> likecollectionList = likecollectionService.list(queryWrapper);
            if(likecollectionList ==null || likecollectionList.size() == 0){
                if(1 == cancellationReq.getType()){
                    return R.failure(ResultCode.FAILURE,"Like canceled");
                }else {
                    return R.failure(ResultCode.FAILURE,"Collection canceled");
                }
            }
            likecollectionService.removeById(likecollectionList.get(0).getId());
            updateSum(2,cancellationReq.getType(),cancellationReq.getPostId());
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
     * @param type 1.点赞 2.收藏
     * @param postId 帖子id
     */
    public void updateSum(int operate ,int type,Long postId ){
        NativeSearchQueryBuilder  query = new NativeSearchQueryBuilder();
        query.withQuery(QueryBuilders.matchQuery("postId", postId));
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()>0){
            postes = searchHitList.get(0).getContent();
        }else {
            return;
        }
        //点赞数
        long followQuantity = postes.getFollowQuantity();
        //评论数量
        long commentNum = postes.getCommentNum();

        //收藏
        long numberOfLikes = postes.getNumberOfLikes();
        Document document = Document.create();
        if(operate == 1){
            if(type == 1){
                document.put("followQuantity", followQuantity+1);
            }else{
                document.put("numberOfLikes", numberOfLikes+1);
            }
        }else{
            if(type == 1){
                document.put("followQuantity", followQuantity-1);
            }else{
                document.put("numberOfLikes", numberOfLikes-1);
            }
        }
        elasticsearchRestTemplate.update(UpdateQuery.builder(postes.getId()).withDocument(document).build(), IndexCoordinates.of(Postes.INDEXNAME));
    }

}
