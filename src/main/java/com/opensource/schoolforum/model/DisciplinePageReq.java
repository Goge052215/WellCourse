package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("学科分页")
public class DisciplinePageReq {


    @ApiModelProperty("pageNo")
    @Min(value = 0,message = "pageNo不能为空")
    private int pageNo ;


    @ApiModelProperty("pageSize")
    @Min(value = 0,message = "pageSize不能为空")
    private  int pageSize ;


    @ApiModelProperty("学校id")
    @Min(value = 0,message = "学校id不能为空")
    private int schoolId;
}
