package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("第二层级评价")
public class SecondCommentReq {

    @ApiModelProperty("第一级评论id")
    @Min(value = 0,message = "第一级评论id不能为空")
    private Long firstCommentId;


   /* @ApiModelProperty("pageNo")
    @Min(value = 0,message = "pageNo不能为空")
    private int pageNo ;


    @ApiModelProperty("pageSize")
    @Min(value = 0,message = "pageSize不能为空")
    private  int pageSize ;*/

}
