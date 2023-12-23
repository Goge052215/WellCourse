package com.opensource.schoolforum.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@ApiModel("用户登录参数")
public class LoginUserReq {

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不合理")
    private String email;


    @NotBlank(message = "密码不能为空")
    @ApiModelProperty("密码")
    private String password;

}
