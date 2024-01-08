package com.opensource.schoolforum.service.impl;

import com.opensource.schoolforum.entity.Emailcode;
import com.opensource.schoolforum.mapper.EmailcodeMapper;
import com.opensource.schoolforum.service.EmailcodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邮箱验证码 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-26
 */
@Service
public class EmailcodeServiceImpl extends ServiceImpl<EmailcodeMapper, Emailcode> implements EmailcodeService {

}
