package com.opensource.schoolforum.model;


import com.opensource.schoolforum.entity.Postes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("帖子详情返回")
public class PostesEsResp {

    @ApiModelProperty("帖子详情")
    private Postes postes;
    @ApiModelProperty("1已赞，0 没有赞")
    private int like;
    @ApiModelProperty("1收藏，0 没有收藏")
    private int collect;
}
