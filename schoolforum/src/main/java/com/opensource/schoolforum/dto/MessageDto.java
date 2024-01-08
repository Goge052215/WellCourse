package com.opensource.schoolforum.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "消息聚合对象", description = "消息聚合对象")
public class MessageDto implements Serializable  {

    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("头像")
    private String headsculpture;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("帖子id")
    private Long postId;
    @ApiModelProperty("帖子主题")
    private String postTitle;
    @ApiModelProperty("1.点赞 2.收藏 3.评论")
    private Integer messageType;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("自增列")
    private Long id;
    @ApiModelProperty("0未读 1已读")
    private Integer readstatus;
    @ApiModelProperty("消息文本")
    private String content;

}
