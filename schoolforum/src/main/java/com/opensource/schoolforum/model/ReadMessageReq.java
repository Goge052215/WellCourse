package com.opensource.schoolforum.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("读消息")
public class ReadMessageReq {


    @ApiModelProperty("消息id")
    @Min(value = 0,message = "消息id不能为空")
    private long id;
}
