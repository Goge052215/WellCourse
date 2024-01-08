package com.opensource.schoolforum.model;


import com.opensource.schoolforum.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel("返回结果")
public class R<T>{

    /**
     * 错误码
     */
    @ApiModelProperty("错误码")
    private Integer code;

    /**
     * 错误消息
     */
    @ApiModelProperty("错误消息")
    private String msg;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private T data;

    public static <U> R<U> toResult(int rows) {
        return rows > 0 ? R.success() : R.failure();
    }


    public static <U> R<U> success() {
        return of(ResultCode.SUCCESS, null, null);
    }

    public static <U> R<U> success(U data) {
        return of(ResultCode.SUCCESS, null, data);
    }

    public static <U> R<U> failure() {
        return of(ResultCode.FAILURE, null, null);
    }

    public static <U> R<U> failure(ResultCode resultCode) {
        return of(resultCode, null, null);
    }

    public static <U> R<U> failure(ResultCode resultCode, String detail) {
        return of(resultCode, detail, null);
    }

    /*public static <U, E extends CodeException> R<U> exception(E ex) {
        return failure(ex.getCode(), ex.getDetail());
    }*/

    public static <U> R<U> of(ResultCode resultCode, String detail, U data) {
        R<U> result = new R<>();
        result.code = resultCode.code();
        if (StringUtils.isEmpty(detail)) {
            result.msg = resultCode.message();
        } else {
            //覆盖消息
            result.msg = detail;
        }
        result.data = data;
        return result;
    }
}
