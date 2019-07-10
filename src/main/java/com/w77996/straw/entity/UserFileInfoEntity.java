package com.w77996.straw.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @description: 用户与文件相关
 * @author: straw
 **/
@Data
public class UserFileInfoEntity {

    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 使用文件总大小
     */
    private Long fileSize;
    /**
     * 剩余空间
     */
    private Long leftSize;
    /**
     * 总共大小
     */
    private Long totalSize;
    /**
     * 文件数量
     */
    private Integer fileNum;
    /**
     * 是否是vip
     */
    @JsonIgnore
    private Integer isVip;
}
