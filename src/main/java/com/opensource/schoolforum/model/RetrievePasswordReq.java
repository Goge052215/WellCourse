package com.opensource.schoolforum.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("找回密码")
public class RetrievePasswordReq {


    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String userEmail;


    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

    @ApiModelProperty("邮箱验证码")
    @NotBlank(message = "邮箱验证码")
    private String code;
}
