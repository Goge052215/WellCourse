package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("第一级插件")
public class FirstPlugListReq {


    @ApiModelProperty("科目id")
    @Min(value = 0,message = "科目id不能为空")
    private long discipineId;

}
