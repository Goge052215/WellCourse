package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("评论参数")
public class AddCommentReq {



    @ApiModelProperty("评论内容")
    @NotBlank(message = "评论内容为空")
    private String content;

    @ApiModelProperty("回复评论的ID，如果为0，则表示该评论是一级评论")
    private Long parentId;


    @ApiModelProperty("帖子id")
    @Min(value = 0,message = "帖子id不能为空")
    private long postId;

}
