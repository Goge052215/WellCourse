package com.opensource.schoolforum.service.impl;

import com.opensource.schoolforum.entity.Postes;
import com.opensource.schoolforum.mapper.PostEsMapper;
import com.opensource.schoolforum.service.PostEsService;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;


@Service
public class PostEsServiceImpl implements PostEsService {

    @Resource
    private PostEsMapper postEsMapper;

    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public void deleteIndex(String index) {
        elasticsearchTemplate.delete(index);
    }

    @Override
    public void save(Postes docBean) {
        postEsMapper.save(docBean);
    }

    @Override
    public void saveAll(List<Postes> list) {
        postEsMapper.saveAll(list);
    }

    @Override
    public Iterator<Postes> findAll() {
        return postEsMapper.findAll().iterator();
    }
}
