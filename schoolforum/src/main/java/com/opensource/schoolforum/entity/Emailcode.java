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
 * 邮箱验证码
 * </p>
 *
 * @author 
 * @since 2023-10-26
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("emailcode")
@ApiModel(value = "Emailcode对象", description = "邮箱验证码")
public class Emailcode implements Serializable {

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

    @ApiModelProperty("验证码")
    @TableField("code")
    private String code;

    @ApiModelProperty("1注册 2找回密码")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;


}
