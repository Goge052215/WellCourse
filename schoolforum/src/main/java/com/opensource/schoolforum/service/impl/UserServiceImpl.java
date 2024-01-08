package com.opensource.schoolforum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.opensource.schoolforum.entity.User;
import com.opensource.schoolforum.mapper.UserMapper;
import com.opensource.schoolforum.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUser(String useremail) {
        QueryWrapper queryWrapperUser = new QueryWrapper();
        queryWrapperUser.eq("useremail", useremail);
        User user = this.baseMapper.selectOne(queryWrapperUser);
        return user;
    }
}
