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
 * 消息
 * </p>
 *
 * @author 
 * @since 2023-10-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("message")
@ApiModel(value = "Message对象", description = "消息")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("发送用户id")
    @TableField("senduserid")
    private Long sendUserid;


    @ApiModelProperty("接收用户id")
    @TableField("receiveuserid")
    private Long receiveUserid;

    @ApiModelProperty("1.点赞 2.收藏 3.评论")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("资源id")
    @TableField("resourcesid")
    private Long resourcesid;

    @ApiModelProperty("0未读 1已读")
    @TableField("readstatus")
    private Integer readstatus;

    @ApiModelProperty("消息内容")
    @TableField("content")
    private String content;


}
