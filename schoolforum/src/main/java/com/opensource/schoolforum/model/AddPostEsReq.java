package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("添加帖子")
public class AddPostEsReq {

    @ApiModelProperty("帖子主题")
    @NotBlank(message = "主题不能为空")
    private String postTitle;


    @ApiModelProperty("使用的插件")
    private Long plugId;

    @ApiModelProperty("一级插件id")
    private Long plugParentId;

    @ApiModelProperty("帖子内容")
    @NotBlank(message = "帖子内容不能为空")
    private String content;

    @ApiModelProperty("学科id")
    @Min(value = 0,message = "学科id不能为空")
    private long disciplineId;

    @ApiModelProperty("附件")
    private String attachmentAddress;


}
