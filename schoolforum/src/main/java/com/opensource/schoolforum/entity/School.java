package com.opensource.schoolforum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学校表
 * </p>
 *
 * @author 
 * @since 2023-10-18
 */
@Data
@Getter
@Setter
@Accessors(chain = true)
@TableName("school")
@ApiModel(value = "School对象", description = "学校表")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增列")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("学校名称")
    @TableField("name")
    private String  name;

    @ApiModelProperty("1.正常2.禁用")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
