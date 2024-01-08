package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("删除敏感词")
public class DeleteSensitiveReq {

    @ApiModelProperty("敏感词id")
    private Long id;



}
