package com.opensource.schoolforum.service.impl;

import com.opensource.schoolforum.entity.Plug;
import com.opensource.schoolforum.mapper.PlugMapper;
import com.opensource.schoolforum.service.PlugService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 插件表 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@Service
public class PlugServiceImpl extends ServiceImpl<PlugMapper, Plug> implements PlugService {


    @Override
    public List<Plug> queryPlugListFromDiscipline(Long disciplineId) {
        return this.baseMapper.queryPlugListFromDiscipline(disciplineId);
    }
}
