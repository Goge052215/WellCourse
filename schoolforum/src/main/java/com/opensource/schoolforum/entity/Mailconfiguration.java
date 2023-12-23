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
 * 邮箱配置
 * </p>
 *
 * @author 
 * @since 2023-10-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("mailconfiguration")
@ApiModel(value = "Mailconfiguration对象", description = "邮箱配置")
public class Mailconfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("邮件服务器的地址")
    @TableField("host")
    private String host;

    @ApiModelProperty("邮件服务器的端口号")
    @TableField("port")
    private String port;

    @ApiModelProperty("发件人的邮箱账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("发件人的邮箱密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("接收邮箱账号")
    @TableField("receiveusername")
    private String receiveusername;

    @ApiModelProperty("是否需要身份验证 1.true2.false")
    @TableField("auth")
    private Boolean auth;

    @ApiModelProperty("是否开启TLS加密 1.true2.false")
    @TableField("starttlsenable")
    private Boolean starttlsenable;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
