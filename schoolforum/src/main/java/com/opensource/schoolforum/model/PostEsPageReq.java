package com.opensource.schoolforum.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("帖子分页参数")
public class PostEsPageReq {

    @ApiModelProperty("学科id")
    @Min(value = 0,message = "学科id不能为空")
    private long disciplineId;

    @ApiModelProperty("pageNo")
    @Min(value = 0,message = "pageNo不能为空")
    private int pageNo ;


    @ApiModelProperty("pageSize")
    @Min(value = 0,message = "pageSize不能为空")
    private  int pageSize ;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("收藏数量排序 asc desc")
    private String numberOfLikesSort;

    @ApiModelProperty("点赞数排序 asc desc ")
    private String followQuantitySort;

    @ApiModelProperty("评论数量排序 asc desc")
    private String commentNumSort;


}
