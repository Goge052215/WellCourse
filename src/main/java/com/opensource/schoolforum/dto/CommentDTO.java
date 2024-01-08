package com.opensource.schoolforum.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@ApiModel(value = "Comment聚合对象", description = "Comment聚合对象")
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userid;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("头像")
    private String headsculpture;

    @ApiModelProperty("回复评论的ID，如果为0，则表示该评论是一级评论")
    private Long parentid;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("帖子id")
    @TableField("postid")
    private Long postid;

    private List<CommentDTO> children;


}