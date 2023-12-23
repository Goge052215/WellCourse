package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("建议参数")
public class AddSuggestionsReq {

    @ApiModelProperty("标题")
    private String subject;

    @ApiModelProperty("内容")
    private String text;
}
