package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.annotate.PassToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.constant.PromptConstant;
import com.opensource.schoolforum.entity.*;
import com.opensource.schoolforum.enums.ResultCode;
import com.opensource.schoolforum.model.*;
import com.opensource.schoolforum.service.*;
import com.opensource.schoolforum.vo.SchoolDisciplinePageModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/postEs")
@Api(value = "帖子", tags = "帖子")
@Slf4j
public class PostEsController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private PostEsService postEsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikecollectionService likecollectionService;

    @Autowired
    private PostService postService;

    @Autowired
    private DisciplineService disciplineService;

    @PostMapping("/postEsPage")
    @ApiOperation("帖子列表-es检索")
    public R<PagerModel<Postes> > postEsPage(@RequestBody @Valid PostEsPageReq postEsPageReq){
        PageRequest pageRequest = PageRequest.of((postEsPageReq.getPageNo()-1),postEsPageReq.getPageSize());
        NativeSearchQueryBuilder  query = new NativeSearchQueryBuilder();
        query.withQuery(QueryBuilders.matchQuery("disciplineId", postEsPageReq.getDisciplineId()));
        //只查询正常的
        query.withFilter(QueryBuilders.matchPhraseQuery("status",1));
        query.withPageable(pageRequest);
        if(SortOrder.DESC.toString().equals(postEsPageReq.getCommentNumSort())){
            query.withSort(SortBuilders.fieldSort("commentNum").order(SortOrder.DESC));
        }
        if(SortOrder.ASC.toString().equals(postEsPageReq.getCommentNumSort())){
            query.withSort(SortBuilders.fieldSort("commentNum").order(SortOrder.ASC));
        }
        if(SortOrder.DESC.toString().equals(postEsPageReq.getFollowQuantitySort())){
            query.withSort(SortBuilders.fieldSort("followQuantity").order(SortOrder.DESC));
        }
        if(SortOrder.ASC.toString().equals(postEsPageReq.getFollowQuantitySort())){
            query.withSort(SortBuilders.fieldSort("followQuantity").order(SortOrder.ASC));
        }
        if(SortOrder.DESC.toString().equals(postEsPageReq.getNumberOfLikesSort())){
            query.withSort(SortBuilders.fieldSort("numberOfLikes").order(SortOrder.DESC));
        }
        if(SortOrder.ASC.toString().equals(postEsPageReq.getNumberOfLikesSort())){
            query.withSort(SortBuilders.fieldSort("numberOfLikes").order(SortOrder.ASC));
        }
        if(StringUtils.isNotEmpty(postEsPageReq.getContent())){
            query.withQuery(QueryBuilders.matchQuery("content",postEsPageReq.getContent()));

        }
        if(StringUtils.isNotEmpty(postEsPageReq.getTitle())){
            query.withQuery(QueryBuilders.matchQuery("postTitle",postEsPageReq.getTitle()));
        }
        query.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));


        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        List<Postes> postesList = new ArrayList<>();
        for (SearchHit<Postes> postesSearchHit : searchHitList){
            postesList.add(postesSearchHit.getContent());
        }
        long totalResult = search.getTotalHits();
        PagerModel<Postes> pagerModel = new PagerModel<>();
        pagerModel.setTotal(totalResult);
        pagerModel.setRows(postesList);
        pagerModel.setSize(searchHitList.size());
        pagerModel.setCurrent(postEsPageReq.getPageSize());
        return R.success(pagerModel);
    }

    @PostMapping("/postEs")
    @ApiOperation("帖子详情-es检索")
    @LoginToken
    public R<PostesEsResp> postEs(@RequestBody @Valid PostEsReq postEsReq){
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery();
        idsQueryBuilder.addIds(postEsReq.getId());
        NativeSearchQueryBuilder  query = new NativeSearchQueryBuilder();
        if(StringUtils.isEmpty(postEsReq.getId())){
            query.withFilter(QueryBuilders.matchPhraseQuery("postId",postEsReq.getPostId()));
        }else{
            query.withQuery(idsQueryBuilder);
        }
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()==0){
            return R.failure(ResultCode.FAILURE, PromptConstant.NO_DATA);
        }
        postes = searchHitList.get(0).getContent();
        QueryWrapper<Likecollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postid",postes.getPostId());
        queryWrapper.eq("type",1);
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        queryWrapper.eq("userid",user.getId());
        List<Likecollection> likecollectionList = likecollectionService.list(queryWrapper);
        PostesEsResp postesEsResp = new PostesEsResp();
        if(likecollectionList !=null && likecollectionList.size()>0){
            postesEsResp.setLike(1);
        }else {
            postesEsResp.setLike(0);
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postid",postes.getPostId());
        queryWrapper.eq("type",2);
        queryWrapper.eq("userid",user.getId());
        List<Likecollection> collectionList = likecollectionService.list(queryWrapper);
        if(collectionList !=null && collectionList.size()>0){
            postesEsResp.setCollect(1);
        }else {
            postesEsResp.setCollect(0);
        }
        postesEsResp.setPostes(postes);
        return R.success(postesEsResp);
    }


    @PostMapping("/postEsById")
    @ApiOperation("帖子详情-es检索")
    @LoginToken
    public R<PostesEsResp> postEsById(@RequestBody @Valid PostEsByIdReq postEsByIdReq){
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.matchPhraseQuery("postId",postEsByIdReq.getId()))
                .build();
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query, Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()==0){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        postes = searchHitList.get(0).getContent();
        QueryWrapper<Likecollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postid",postes.getPostId());
        queryWrapper.eq("type",1);
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        queryWrapper.eq("userid",user.getId());
        List<Likecollection> likecollectionList = likecollectionService.list(queryWrapper);
        PostesEsResp postesEsResp = new PostesEsResp();
        if(likecollectionList !=null && likecollectionList.size()>0){
            postesEsResp.setLike(1);
        }else {
            postesEsResp.setLike(0);
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("postid",postes.getPostId());
        queryWrapper.eq("type",2);
        queryWrapper.eq("userid",user.getId());
        List<Likecollection> collectionList = likecollectionService.list(queryWrapper);
        if(collectionList !=null && collectionList.size()>0){
            postesEsResp.setCollect(1);
        }else {
            postesEsResp.setCollect(0);
        }
        postesEsResp.setPostes(postes);
        return R.success(postesEsResp);
    }


    @PostMapping("/myPostEsPage")
    @ApiOperation("我的帖子列表")
    @LoginToken
    public R<PagerModel<Postes> > myPostEsPage(@RequestBody @Valid MyPostEsPageReq postEsPageReq){
        String userEmail =  BaseUserInfo.getUserId();
        PageRequest pageRequest = PageRequest.of((postEsPageReq.getPageNo()-1),postEsPageReq.getPageSize());
        NativeSearchQueryBuilder  query = new NativeSearchQueryBuilder();
        //query.withQuery(QueryBuilders.termQuery("userEmail", userEmail));
        //只查看正常和禁用的
        //query.withFilter(new RangeQueryBuilder("status").lt(3));
        List<Integer> statusList = new ArrayList<>();
        statusList.add(1);
        statusList.add(2);
        query.withFilter(QueryBuilders.matchPhraseQuery("status",statusList));
        query.withFilter(QueryBuilders.matchPhraseQuery("userEmail",userEmail));
        query.withPageable(pageRequest);
        if(SortOrder.DESC.toString().equals(postEsPageReq.getCommentNumSort())){
            query.withSort(SortBuilders.fieldSort("commentNum").order(SortOrder.DESC));
        }
        if(SortOrder.ASC.toString().equals(postEsPageReq.getCommentNumSort())){
            query.withSort(SortBuilders.fieldSort("commentNum").order(SortOrder.ASC));
        }
        if(SortOrder.DESC.toString().equals(postEsPageReq.getFollowQuantitySort())){
            query.withSort(SortBuilders.fieldSort("followQuantity").order(SortOrder.DESC));
        }
        if(SortOrder.ASC.toString().equals(postEsPageReq.getFollowQuantitySort())){
            query.withSort(SortBuilders.fieldSort("followQuantity").order(SortOrder.ASC));
        }
        if(SortOrder.DESC.toString().equals(postEsPageReq.getNumberOfLikesSort())){
            query.withSort(SortBuilders.fieldSort("numberOfLikes").order(SortOrder.DESC));
        }
        if(SortOrder.ASC.toString().equals(postEsPageReq.getNumberOfLikesSort())){
            query.withSort(SortBuilders.fieldSort("numberOfLikes").order(SortOrder.ASC));
        }
        if(StringUtils.isNotEmpty(postEsPageReq.getContent())){
            query.withQuery(QueryBuilders.matchQuery("content",postEsPageReq.getContent()));

        }
        if(StringUtils.isNotEmpty(postEsPageReq.getTitle())){
            query.withQuery(QueryBuilders.matchQuery("title",postEsPageReq.getTitle()));
        }
        query.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        List<Postes> postesList = new ArrayList<>();
        for (SearchHit<Postes> postesSearchHit : searchHitList){
            postesList.add(postesSearchHit.getContent());
        }
        long totalResult = search.getTotalHits();
        PagerModel<Postes> pagerModel = new PagerModel<>();
        pagerModel.setTotal(totalResult);
        pagerModel.setRows(postesList);
        pagerModel.setSize(searchHitList.size());
        pagerModel.setCurrent(postEsPageReq.getPageSize());
        return R.success(pagerModel);
    }

    @PostMapping("/postEsPageByUser")
    @ApiOperation("用户详情页列表")
    @LoginToken
    public R<PagerModel<Postes> > postEsPageByUser(@RequestBody @Valid PostEsPageByUserReq postEsPageByUserReq){
        PageRequest pageRequest = PageRequest.of((postEsPageByUserReq.getPageNo()-1),postEsPageByUserReq.getPageSize());
        NativeSearchQueryBuilder  query = new NativeSearchQueryBuilder();
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        if(1!=user.getRole()){
            query.withFilter(QueryBuilders.matchPhraseQuery("status",1));
        }
        query.withFilter(QueryBuilders.matchPhraseQuery("userEmail",postEsPageByUserReq.getUserEmail()));
        query.withPageable(pageRequest);
        query.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        List<Postes> postesList = new ArrayList<>();
        for (SearchHit<Postes> postesSearchHit : searchHitList){
            postesList.add(postesSearchHit.getContent());
        }
        long totalResult = search.getTotalHits();
        PagerModel<Postes> pagerModel = new PagerModel<>();
        pagerModel.setTotal(totalResult);
        pagerModel.setRows(postesList);
        pagerModel.setSize(searchHitList.size());
        pagerModel.setCurrent(postEsPageByUserReq.getPageSize());
        return R.success(pagerModel);
    }

    @PostMapping("/deletePostEs")
    @ApiOperation("删除帖子")
    @LoginToken
    public R<?> deletePostEs(@RequestBody @Valid DeleteEsReq deleteEsReq){
        String userEmail =  BaseUserInfo.getUserId();
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery();
        idsQueryBuilder.addIds(deleteEsReq.getId());
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(idsQueryBuilder)
                .build();
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query, Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()==0){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        postes = searchHitList.get(0).getContent();
        if(!userEmail.equals(postes.getUserEmail())){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_PERMISSION_NO_DATA);
        }
        Post post = postService.getById(postes.getPostId());
        post.setStatus(3);
        postService.saveOrUpdate(post);
        postes.setStatus(3);
        postes.setUpdateTime(new Date());
        elasticsearchRestTemplate.save(postes);
        return R.success();
    }


    @PostMapping("/addPostEs")
    @ApiOperation("新增帖子")
    @LoginToken
    public R<?> addPostEs(@RequestBody @Valid AddPostEsReq addPostEsReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        Post post  = new Post();
        post.setDisciplineid(addPostEsReq.getDisciplineId());
        post.setPlugid(addPostEsReq.getPlugId());
        post.setTitle(addPostEsReq.getPostTitle());
        post.setUserid(user.getId());
        post.setAttachmentaddress(addPostEsReq.getAttachmentAddress());
        post.setStatus(1);
        postService.save(post);
        Postes postes = new Postes();
        postes.setCommentNum(0);
        postes.setUserRole("普通用户");
        postes.setContent(addPostEsReq.getContent());
        postes.setDisciplineId(addPostEsReq.getDisciplineId());
        postes.setFollowQuantity(0);
        postes.setUserEmail(user.getUseremail());
        postes.setPostTitle(addPostEsReq.getPostTitle());
        postes.setUserName(user.getUsername());
        postes.setPostId(post.getId());
        postes.setNumberOfLikes(0);
        postes.setAttachmentAddress(addPostEsReq.getAttachmentAddress());
        postes.setHeadsculpture(user.getHeadsculpture());
        postes.setPlugId(addPostEsReq.getPlugId());
        postes.setPlugParentId(addPostEsReq.getPlugParentId());
        postes.setStatus(1);
        Date date = new Date();
        postes.setCreateTime(date);
        postes.setUpdateTime(date);
        Discipline discipline = disciplineService.getById(addPostEsReq.getDisciplineId());
        postes.setDisciplineName(discipline.getName());
        postes.setSchoolId(discipline.getSchoolid());
        postes = elasticsearchRestTemplate.save(postes);
        return  R.success(postes.getId());
    }


    @PostMapping("/updatePostEs")
    @ApiOperation("修改帖子")
    @LoginToken
    public R<?> updatePostEs(@RequestBody @Valid UpdatePostEsReq apdatePostEsReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        Post post  = postService.getById(apdatePostEsReq.getPostId());
        if(post ==null){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        if(post.getUserid() !=user.getId()){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_PERMISSION_NO_DATA);
        }
        post.setDisciplineid(apdatePostEsReq.getDisciplineId());
        post.setPlugid(apdatePostEsReq.getPlugId());
        post.setTitle(apdatePostEsReq.getPostTitle());
        post.setUserid(user.getId());
        post.setAttachmentaddress(apdatePostEsReq.getAttachmentAddress());
        post.setStatus(1);
        postService.saveOrUpdate(post);
        //修改es
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery();
        idsQueryBuilder.addIds(apdatePostEsReq.getId());
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(idsQueryBuilder)
                .build();
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query, Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()==0){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        postes = searchHitList.get(0).getContent();
        if(!userEmail.equals(postes.getUserEmail())){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_PERMISSION_NO_DATA);
        }
        postes = searchHitList.get(0).getContent();
        if(null !=apdatePostEsReq.getPostTitle()){
            postes.setPostTitle(apdatePostEsReq.getPostTitle());
        }
        if(null !=apdatePostEsReq.getContent()){
            postes.setContent(apdatePostEsReq.getContent());
        }
        if(null !=apdatePostEsReq.getPostTitle()){
            postes.setPostTitle(apdatePostEsReq.getPostTitle());
        }
        if(null != apdatePostEsReq.getPlugParentId() && 0L!=apdatePostEsReq.getPlugParentId()){
            postes.setPlugParentId(apdatePostEsReq.getPlugParentId());
        }
        if(null !=apdatePostEsReq.getPlugId() && 0L!=apdatePostEsReq.getPostId()){
            postes.setPlugId(apdatePostEsReq.getPlugId());
        }
        if(null != apdatePostEsReq.getAttachmentAddress()){
            postes.setAttachmentAddress(apdatePostEsReq.getAttachmentAddress());
        }
        postes.setUpdateTime(new Date());
        postes.setStatus(1);
        elasticsearchRestTemplate.save(postes);
        return  R.success(postes.getId());
    }

    @PostMapping("/updatePostEsStatus")
    @ApiOperation("禁用帖子")
    @LoginToken
    @Transactional
    public R<?> updatePostEsStatus(@RequestBody @Valid UpdatePostEsStatusReq updatePostEsStatusReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        if(2==user.getRole()){
            return R.failure(ResultCode.ERROR,PromptConstant.NO_PERMISSION);
        }
        Post post  = postService.getById(updatePostEsStatusReq.getPostId());
        if(post ==null){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        post.setStatus(2);
        postService.saveOrUpdate(post);
        //发送消息
        Message message = new Message();
        message.setReadstatus(0);
        message.setType(4);
        message.setReceiveUserid(post.getUserid());
        message.setSendUserid(user.getId());
        message.setResourcesid(post.getId());
        message.setContent(updatePostEsStatusReq.getReason());
        messageService.save(message);
        //修改es
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        query.withFilter(QueryBuilders.matchPhraseQuery("postId",updatePostEsStatusReq.getPostId()));
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query.build(), Postes.class);
        List<SearchHit<Postes>> searchHitList =  search.getSearchHits();
        Postes postes = null;
        if(searchHitList.size()==0){
            return R.failure(ResultCode.FAILURE,PromptConstant.NO_DATA);
        }
        postes = searchHitList.get(0).getContent();
        postes.setStatus(2);
        postes.setReason(updatePostEsStatusReq.getReason());
        postes.setUpdateTime(new Date());
        elasticsearchRestTemplate.save(postes);
        return  R.success();
    }



}
