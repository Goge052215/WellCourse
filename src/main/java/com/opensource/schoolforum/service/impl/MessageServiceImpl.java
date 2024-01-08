package com.opensource.schoolforum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.dto.MessageDto;
import com.opensource.schoolforum.entity.Message;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.mapper.MessageMapper;
import com.opensource.schoolforum.model.PagerModel;
import com.opensource.schoolforum.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-28
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {


    @Override
    public PagerModel<MessageDto> messagePager(Page<MessageDto> page, Long userId,Integer type) {
        LambdaQueryWrapper<MessageDto> lambda = new LambdaQueryWrapper<>();
        lambda.apply(type != null, "message.type = {0}", type);
        lambda.apply(userId != null, "message.receiveuserid = {0}", userId);
        Page<MessageDto> messagePage =  baseMapper.queryList(page,lambda,userId);
        PagerModel<MessageDto> messagePagerMode = new PagerModel<>();
        messagePagerMode.setCurrent(messagePage.getCurrent());
        messagePagerMode.setPages(messagePage.getPages());
        messagePagerMode.setSize(messagePage.getSize());
        messagePagerMode.setRows(messagePage.getRecords());
        messagePagerMode.setTotal(messagePage.getTotal());
        return messagePagerMode;
    }
}
