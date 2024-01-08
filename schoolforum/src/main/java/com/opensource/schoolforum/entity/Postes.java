package com.opensource.schoolforum.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = Postes.INDEXNAME)
@Setting(shards = 3, replicas = 1)
@Getter
@Setter
@Accessors(chain = true)
@TableName("Postes")
@ApiModel(value = "帖子es", description = "帖子es")
public class Postes implements Serializable {

    public static final String INDEXNAME = "schoolforum";

    @ApiModelProperty("es随机生成id")
    @Id
    private String id;

    @ApiModelProperty("帖子自增id")
    @Field(name = "postId")
    private Long postId;

    @ApiModelProperty("用户邮件")
    @Field(name = "userEmail")
    private String userEmail;

    @ApiModelProperty("用户姓名")
    @Field(name = "userName")
    private String userName;

    @ApiModelProperty("用户角色")
    @Field(name = "userRole")
    private String userRole;

    @ApiModelProperty("帖子主题")
    @Field(name = "postTitle")
    private String postTitle;

    @ApiModelProperty("二级插件id")
    @Field(name = "plugId")
    private Long plugId;

    @ApiModelProperty("一级插件id")
    @Field(name = "plugParentId")
    private Long plugParentId;


    @ApiModelProperty("帖子内容")
    @Field(name = "content")
    private String content;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Field(type = FieldType.Date)
    private Date createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Field(type = FieldType.Date)
    private Date updateTime;

    @ApiModelProperty("收藏数量")
    @Field(name = "numberOfLikes")
    private long numberOfLikes;

    @ApiModelProperty("点赞数")
    @Field(name = "followQuantity")
    private long followQuantity;

    @ApiModelProperty("评论数量")
    @Field(name = "commentNum")
    private long commentNum;

    @ApiModelProperty("学科id")
    @Field(name = "disciplineId")
    private long disciplineId;

    @ApiModelProperty("学科名称")
    @Field(name = "disciplineName")
    private String disciplineName;

    @ApiModelProperty("附件地址")
    @Field(name = "attachmentAddress")
    private String attachmentAddress;


    @ApiModelProperty("头像")
    @Field("headsculpture")
    private String headsculpture;

    @ApiModelProperty("1.正常2.禁用")
    @Field("status")
    private int status;

    @ApiModelProperty("学校id")
    @Field("schoolId")
    private long schoolId;


    @ApiModelProperty("禁用原因")
    @Field("reason")
    private String reason;


}
