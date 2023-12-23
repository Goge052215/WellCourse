package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("删除我的帖子")
public class DeleteEsReq {

    @ApiModelProperty("es随机生成主键id")
    @NotBlank(message = "id不能为空")
    private String id;



}
