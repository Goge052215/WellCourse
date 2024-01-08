package com.opensource.schoolforum.mapper;

import com.opensource.schoolforum.entity.Postes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  PostEsMapper extends ElasticsearchRepository<Postes, Long> {


}
