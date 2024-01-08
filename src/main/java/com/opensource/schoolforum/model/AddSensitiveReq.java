package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("添加敏感词")
public class AddSensitiveReq {

    @ApiModelProperty("敏感词")
    @NotBlank(message = "敏感词不能为空")
    private String word;



}
