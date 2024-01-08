package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("修改帖子")
public class UpdatePostEsReq {


    @ApiModelProperty("帖子id")
    @Min(value = 0,message = "帖子id不能为空")
    private long postId;

    @ApiModelProperty("es随机生成主键id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty("帖子主题")
    @NotBlank(message = "主题不能为空")
    private String postTitle;

    @ApiModelProperty("二级插件id")
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
