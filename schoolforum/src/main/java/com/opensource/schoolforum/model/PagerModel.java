package com.opensource.schoolforum.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@ApiModel(value = "自定义分页列表",description = "自定义分页列表")
public class PagerModel<T> implements Serializable {
    private static final long serialVersionUID = 4804053559968742915L;
    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    private long total;
    /**
     * 每页的查询结果集
     */
    @ApiModelProperty("每页的查询结果集")
    private List<T> rows = new ArrayList();
    /**
     * 获取总页数
     */
    @ApiModelProperty("获取总页数")
    private long pages;
    /**
     * 获取当前页
     */
    @ApiModelProperty("获取当前页")
    private long current;
    /**
     * 当前页显示几条数据
     */
    @ApiModelProperty("当前页显示几条数据")
    private long size;
    /**
     * 当前页是否有下一页
     */
    @ApiModelProperty("当前页是否有下一页")
    private boolean hasNext;
    /**
     * 当前页是否有上一页
     */
    @ApiModelProperty("当前页是否有上一页")
    private boolean hasPrevious;

    public PagerModel() {
    }

    public PagerModel(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PagerModel(long total, List<T> rows, long pages, long current) {
        this.total = total;
        this.rows = rows;
        this.pages = pages;
        this.current = current;
    }

    public PagerModel(long total, List<T> rows, long pages, long current, long size) {
        this.total = total;
        this.rows = rows;
        this.pages = pages;
        this.current = current;
        this.size = size;
    }

    public PagerModel(long total, List<T> rows, long pages, long current, boolean hasNext, boolean hasPrevious) {
        this.total = total;
        this.rows = rows;
        this.pages = pages;
        this.current = current;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public PagerModel(long total, List<T> rows, long pages, long current, long size, boolean hasNext, boolean hasPrevious) {
        this.total = total;
        this.rows = rows;
        this.pages = pages;
        this.current = current;
        this.size = size;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}

