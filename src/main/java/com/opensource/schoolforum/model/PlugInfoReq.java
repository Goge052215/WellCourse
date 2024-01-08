package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("插件详情")
public class PlugInfoReq {


    @ApiModelProperty("id")
    @Min(value = 0,message = "id")
    private long id;

}
