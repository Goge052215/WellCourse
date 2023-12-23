package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("取消点赞收藏参数")
public class CancellationReq {
    @ApiModelProperty("1.点赞2.收藏")
    @Min(value = 0,message = "类型不能为空")
    private int type;

    @ApiModelProperty("帖子id")
    @Min(value = 0,message = "帖子id不能为空")
    private Long postId;
}
