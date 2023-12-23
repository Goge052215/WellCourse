package com.opensource.schoolforum.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("修改密码")
public class UpdateUserPasswordReq {


    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;

}
