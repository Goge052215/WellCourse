package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("禁用帖子")
public class UpdatePostEsStatusReq implements Serializable {


    @ApiModelProperty("帖子id")
    @Min(value = 0,message = "帖子id不能为空")
    private long postId;

    @ApiModelProperty("禁用原因")
    private String reason;


}
