package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel("消息分页")
public class MessagePageReq {


    @ApiModelProperty("pageNo")
    @Min(value = 0,message = "pageNo不能为空")
    private int pageNo ;


    @ApiModelProperty("pageSize")
    @Min(value = 0,message = "pageSize不能为空")
    private  int pageSize ;

    @ApiModelProperty("1.点赞 2.收藏 3.评论")
    @Min(value = 0,message = "类型不能为空")
    private int type;

}
