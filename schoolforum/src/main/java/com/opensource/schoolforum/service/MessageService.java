package com.opensource.schoolforum.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.dto.MessageDto;
import com.opensource.schoolforum.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.model.PagerModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 消息 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-28
 */
public interface MessageService extends IService<Message> {


    public PagerModel<MessageDto>  messagePager(Page<MessageDto> page , Long userId,Integer type);

}
