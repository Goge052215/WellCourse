package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel("修改用户信息")
public class UpdateUserInfoReq {


    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空")
    private String headSculpture;


}
