package com.w77996.straw.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @description: 文件类型
 * @author: straw
 **/
@Data
public class FileEntity {

    private Long id;
    /**
     * 用户名
     */
    private Integer userId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件Url
     */
    private String fileUrl;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 额外属性
     */
    private String props;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 文件状态
     */
    @JsonIgnore
    private String status;
}
