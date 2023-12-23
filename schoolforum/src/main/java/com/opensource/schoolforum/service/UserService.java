package com.opensource.schoolforum.service;

import com.opensource.schoolforum.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
public interface UserService extends IService<User> {

    public User getUser(String useremail);

}
