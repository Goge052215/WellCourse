package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("第二级插件")
public class SecondPlugListReq {


    @ApiModelProperty("父类id")
    @Min(value = 0,message = "父类id不能为空")
    private long parentId;

}
