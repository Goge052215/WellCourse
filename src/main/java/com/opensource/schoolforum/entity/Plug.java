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
 * 插件表
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("plug")
@ApiModel(value = "Plug对象", description = "插件表")
public class Plug implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("插件名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("插件代码")
    @TableField("code")
    private String code;

    @ApiModelProperty("备注")
    @TableField("remake")
    private String remake;

    @ApiModelProperty("1.正常2.禁用")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


    @ApiModelProperty("父类0 二级为父类id")
    @TableField("parentid")
    private Long parentid;

}
