package com.opensource.schoolforum.service;

import com.opensource.schoolforum.entity.Plug;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 插件表 服务类
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
public interface PlugService extends IService<Plug> {

        public List<Plug> queryPlugListFromDiscipline(Long disciplineId);
}
