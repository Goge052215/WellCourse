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
 * 组件科目对应关系
 * </p>
 *
 * @author 
 * @since 2023-10-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("plugdiscipline")
@ApiModel(value = "Plugdiscipline对象", description = "组件科目对应关系")
public class Plugdiscipline implements Serializable {

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

    @ApiModelProperty(" 组件id")
    @TableField("plugid")
    private Long plugid;

    @ApiModelProperty(" 科目id")
    @TableField("discipineid")
    private Long discipineid;


}
