package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("根据邮箱查询用户")
public class UserInfoByEmailReq {




    @ApiModelProperty("用户邮箱")
    private String  userEmail;


}
