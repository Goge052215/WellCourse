package com.opensource.schoolforum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("comment")
@ApiModel(value = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户id")
    @TableField("userid")
    private Long userid;

    @ApiModelProperty("用户名称")
    @TableField("username")
    private String username;

    @ApiModelProperty("头像")
    @TableField("headsculpture")
    private String headsculpture;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("回复评论的ID，如果为0，则表示该评论是一级评论")
    @TableField("parentid")
    private Long parentid;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("帖子id")
    @TableField("postid")
    private Long postid;


}
