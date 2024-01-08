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
 * 邮件
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("email")
@ApiModel(value = "Email对象", description = "邮件")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("标题")
    @TableField("subject")
    private String subject;

    @ApiModelProperty("内容")
    @TableField("text")
    private String text;

    @ApiModelProperty("提交用户")
    @TableField("userid")
    private Long userid;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
