package com.opensource.schoolforum;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensource.schoolforum.entity.Postes;
import com.opensource.schoolforum.service.PostEsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONString;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.*;

@SpringBootTest
@Slf4j
public class EsApplicationTests {



    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private PostEsService postEsService;

    @Test
    void createIndex() {
        // 从 spring data es 4.0开始所有索引操作都在这个接口
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Postes.class);
        // 是否存在，存在则删除
        if(indexOperations.exists()){
            indexOperations.delete();
        }
        // 创建索引
        indexOperations.create();
        // createMapping 根据实体类获取映射关系
        // putMapping 把映射关系添加到索引中
        Document mapping = indexOperations.createMapping(Postes.class);
        indexOperations.putMapping(mapping);
        savaDocument();
        //listPageMatch();
    }

    @Test
    void savaDocument(){

        Postes postes = new Postes();
        postes.setAttachmentAddress("https://img1.baidu.com/it/u=1890390320,3399874998&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800");
        postes.setUserRole("普通用户");
        postes.setPostId(1L);
        postes.setDisciplineName("测试学科名字");
        postes.setDisciplineId(1L);
        postes.setCreateTime(new Date());
        postes.setPostTitle("java之父");
        postes.setPlugId(1L);
        postes.setUserName("Leo");
        postes.setUserEmail("Leo@yahu.com");
        postes.setFollowQuantity(0);
        postes.setNumberOfLikes(0);
        postes.setCommentNum(0);
        postes.setAttachmentAddress(null);
        postes.setUpdateTime(new Date());
        postes.setStatus(1);
        postes.setHeadsculpture("8214222d-29e5-411a-9b25-33160f066100.jpg");
        postes.setContent("Trump and members of his family are being sued for fraud.\n" +
                "\n" +
                "Former U.S. President Donald Trump, his children Donald Jr., Eric, and Ivanka are all being sued by the New York state attorney general, Letitia James, for financial fraud.\n" +
                "\n" +
                "New York state's attorney general is claiming numerous acts of fraud and misrepresentations. Trump and members of his family stand accused of misstating the values of real estate properties to obtain favorable loans and tax benefits.");

        postEsService.save(postes);
        postes = new Postes();
        postes.setStatus(1);
        postes.setAttachmentAddress("https://img1.baidu.com/it/u=1890390320,3399874998&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800");
        postes.setUserRole("学生");
        postes.setDisciplineId(1L);
        postes.setDisciplineName("测试学科名字");
        postes.setPostId(2L);
        postes.setCreateTime(new Date());
        postes.setPostTitle("shell之父");
        postes.setPlugId(2L);
        postes.setUserName("shell");
        postes.setUserEmail("shell@yahu.com");
        postes.setFollowQuantity(0);
        postes.setNumberOfLikes(0);
        postes.setCommentNum(0);
        postes.setAttachmentAddress(null);
        postes.setUpdateTime(new Date());
        postes.setHeadsculpture("8214222d-29e5-411a-9b25-33160f066100.jpg");
        postes.setContent("Trump and members of his family are being sued for fraud.\n" +
                "\n" +
                "Former U.S. President Donald Trump, his children Donald Jr., Eric, and Ivanka are all being sued by the New York state attorney general, Letitia James, for financial fraud.\n" +
                "\n" +
                "New York state's attorney general is claiming numerous acts of fraud and misrepresentations. Trump and members of his family stand accused of misstating the values of real estate properties to obtain favorable loans and tax benefits.");

        postEsService.save(postes);
    }


    /**
     * 分页查询
     * 带参数
     */
    @Test
    public void listPageMatch() {
        int pageNo = 1;
        int pageSize = 5;

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
       // nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("name", "小"));

        //注：Pageable类中 pageNum需要减1,如果是第一页 数值为0
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<Postes> searchHitsResult = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Postes.class);
        //7.获取分页数据
        SearchPage<Postes> searchPageResult = SearchHitSupport.searchPageFor(searchHitsResult, pageable);
        System.out.println("分页查询");
        System.out.println(String.format("totalPages:%d, pageNo:%d, size:%d", searchPageResult.getTotalPages(), pageNo, pageSize));
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();
        System.out.println("------------------------"+gson.toJson(searchPageResult.getSearchHits()));
        searchPageResult.getSearchHits().getSearchHits().size();
    }

    @Test
    public void listPageMatch2() {
        // 设置高亮字段
        PageRequest pageRequest = PageRequest.of((1-1),5);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                //.withQuery(queryBuilder)
                .withPageable(pageRequest)
                //.withHighlightBuilder(highlightBuilder)
                .build();
        SearchHits<Postes> search = elasticsearchRestTemplate.search(query, Postes.class);
        System.out.println(search);
    }
}
