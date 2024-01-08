package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("删除评论参数")
public class DelCommentReq {


    @ApiModelProperty("评论id")
    @Min(value = 0,message = "评论id不能为空")
    private Long  commentId;
}
