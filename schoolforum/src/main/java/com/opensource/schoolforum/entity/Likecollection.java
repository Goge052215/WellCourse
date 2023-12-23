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
 * 点赞收藏
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("likecollection")
@ApiModel(value = "Likecollection对象", description = "点赞收藏")
public class Likecollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("1.点赞2.收藏")
    @TableField("type")
    private int type;

    @ApiModelProperty("帖子id")
    @TableField("postid")
    private Long postid;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("点赞收藏者")
    @TableField("userid")
    private Long userId;


}
