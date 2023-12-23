package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("注册参数")
public class RegisterReq {


    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String userEmail;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空")
    private String headSculpture;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @ApiModelProperty("邮箱验证码")
    @NotBlank(message = "邮箱验证码")
    private String code;

}
