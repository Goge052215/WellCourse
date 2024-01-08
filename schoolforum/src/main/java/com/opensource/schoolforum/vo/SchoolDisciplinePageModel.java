package com.opensource.schoolforum.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "学校科目列表",description = "学校科目列表")
public class SchoolDisciplinePageModel {

    @ApiModelProperty(name = "学校id",notes = "学校id")
    private Long schoolId;

    @ApiModelProperty(name = "科目id",notes = "科目id")
    private Long disciplineId;

    @ApiModelProperty(name = "学校名称",notes = "学校名称")
    private String schoolName;
    @ApiModelProperty(name = "科目名称",notes = "科目名称")
    private String disciplineName;

}
