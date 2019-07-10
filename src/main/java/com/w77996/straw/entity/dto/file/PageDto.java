package com.w77996.straw.entity.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @description: 分页处理
 * @author: straw
 **/
@Data
public class PageDto {

    /**
     * 页数
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 搜索关键字
     */
    private String searchValue;

    /**
     * 开始数
     */
    @JsonIgnore
    private Integer limitStart;

    @JsonIgnore
    private Integer limitEnd;

    public Integer getLimitStart() {
        if (pageNum == null || pageSize == null) {
            pageNum = 0;
            pageSize = 10;
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize > 50 || pageSize <= 0) {
            pageSize = 10;
        }
        return (pageNum - 1) * pageSize;
    }

    public Integer getLimitEnd() {
        return pageSize == null ? 10 : pageSize;
    }
}
