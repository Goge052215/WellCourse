package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("帖子参数")
public class PostEsByIdReq {

    @ApiModelProperty("id")
    private long id;


}
