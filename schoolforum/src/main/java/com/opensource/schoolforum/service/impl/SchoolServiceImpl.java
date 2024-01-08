package com.opensource.schoolforum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.opensource.schoolforum.entity.School;
import com.opensource.schoolforum.mapper.SchoolMapper;
import com.opensource.schoolforum.model.PagerModel;
import com.opensource.schoolforum.service.SchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opensource.schoolforum.vo.SchoolDisciplinePageModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学校表 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@Service
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements SchoolService {



    @Override
    public Page<School> page(Page<School> page, QueryWrapper<School> queryWrapper) {
        return this.baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public PagerModel<School> pageList(Page<School> page, QueryWrapper<School> queryWrapper) {
        Page<School> userIPage = this.baseMapper.selectPage(page, queryWrapper);
        // 分页的所有数据都在userPage对象中封装着
        // 获取总页数
        long pages = userIPage.getPages();
        //一页显示几条数据
        long size = userIPage.getSize();
        // 获取当前页
        long current = userIPage.getCurrent();
        // 获取当前页数据集合
        List<School> records = userIPage.getRecords();
        // 获取总记录数
        long total = userIPage.getTotal();
        // 当前页是否有下一页
        boolean hasNext = userIPage.hasNext();
        // 当前页是否有上一页
        boolean hasPrevious = userIPage.hasPrevious();
        System.out.println("总页数pages=" + pages);
        System.out.println("当前页current=" + current);
        System.out.println("当前页显示几条数据size=" + size);
        System.out.println("当前页数据集合records=" + records);
        System.out.println("总记录数total=" + total);
        System.out.println("是否有下一页hasNext=" + hasNext);
        System.out.println("是否有上一页hasPrevious=" + hasPrevious);
        return new PagerModel<>(total, records,current,size);
    }

    @Override
    public PagerModel<SchoolDisciplinePageModel> schoolDisciplinePageModelPage(Page<SchoolDisciplinePageModel> page, QueryWrapper<SchoolDisciplinePageModel> queryWrapper) {
        Page<SchoolDisciplinePageModel> schoolDisciplinePage =  this.baseMapper.pageQuery(page,queryWrapper);
        // 获取总页数
        long pages = schoolDisciplinePage.getPages();
        //一页显示几条数据
        long size = schoolDisciplinePage.getSize();
        // 获取当前页
        long current = schoolDisciplinePage.getCurrent();
        // 获取当前页数据集合
        List<SchoolDisciplinePageModel> records = schoolDisciplinePage.getRecords();
        // 获取总记录数
        long total = schoolDisciplinePage.getTotal();
        // 当前页是否有下一页
        boolean hasNext = schoolDisciplinePage.hasNext();
        // 当前页是否有上一页
        boolean hasPrevious = schoolDisciplinePage.hasPrevious();
        return new PagerModel<>(schoolDisciplinePage.getTotal(), records);
    }
}
