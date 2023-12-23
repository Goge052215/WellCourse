package com.opensource.schoolforum.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.annotate.LoginToken;
import com.opensource.schoolforum.config.BaseUserInfo;
import com.opensource.schoolforum.dto.MessageDto;
import com.opensource.schoolforum.entity.Likecollection;
import com.opensource.schoolforum.entity.Message;
import com.opensource.schoolforum.entity.School;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.model.*;
import com.opensource.schoolforum.service.MessageService;
import com.opensource.schoolforum.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author 
 * @since 2023-10-28
 */
@RestController
@RequestMapping("/message")
@Api(value = "消息通知", tags = "消息通知")
@Slf4j
public class MessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    @PostMapping("/messageList")
    @ApiOperation("消息列表")
    @LoginToken
    public R<PagerModel<MessageDto>> messageList(@RequestBody @Valid MessagePageReq messagePageReq){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        /*QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",user.getId());
        // 分页插件
        Page<Message> page = new Page(messagePageReq.getPageNo(), messagePageReq.getPageSize());
        Page<Message> messagePager =  messageService.page(page, queryWrapper);
        PagerModel<Message> messagePagerMode = new PagerModel<>();
        messagePagerMode.setCurrent(messagePager.getCurrent());
        messagePagerMode.setPages(messagePager.getPages());
        messagePagerMode.setSize(messagePager.getSize());
        messagePagerMode.setRows(messagePager.getRecords());
        messagePagerMode.setTotal(messagePager.getTotal());*/
        Page<MessageDto> page = new Page(messagePageReq.getPageNo(), messagePageReq.getPageSize());
        PagerModel<MessageDto> messagePagerMode = messageService.messagePager(page,user.getId(),messagePageReq.getType());
        return R.success(messagePagerMode);
    }


    @PostMapping("/readMessage")
    @ApiOperation("读消息")
    @LoginToken
    public R<List<Message>> readMessage(@RequestBody @Valid ReadMessageReq readMessageReq){
        Message message = new Message();
        message.setId(readMessageReq.getId());
        message.setReadstatus(1);
        messageService.updateById(message);
        return R.success();
    }


    @PostMapping("/deleteAll")
    @ApiOperation("清空消息")
    @LoginToken
    public R<List<Message>> deleteAll(){
        String userEmail =  BaseUserInfo.getUserId();
        User user = userService.getUser(userEmail);
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid",user.getId());
        messageService.remove(queryWrapper);
        return R.success();
    }

}
