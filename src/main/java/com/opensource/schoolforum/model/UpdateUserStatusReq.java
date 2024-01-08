package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("修改用户状态")
public class UpdateUserStatusReq {


    @ApiModelProperty("用户id")
    @Min(value = 0,message = "用户id不能为空")
    private Long userId;


    @ApiModelProperty("1.正常2.禁用")
    @Min(value = 0,message = "状态不能为空")
    private int status;
}
