package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("帖子参数")
public class PostEsReq {

    @ApiModelProperty("es随机生成主键id")
    private String id;


    @ApiModelProperty("postId")
    private Long postId;


}
