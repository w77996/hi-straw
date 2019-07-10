package com.w77996.straw.entity.dto.file;

import lombok.Data;

/**
 * @description: sms图床实体类
 * @author: straw
 **/
@Data
public class SmsFileDto {
    /**
     * 宽度
     */
    private int weight;
    /**
     * 高度
     */
    private int height;
    /**
     * 文件名
     */
    private String filename;
    /**
     * 存储的图片名
     */
    private String storename;
    /**
     * 文件大小
     */
    private int size;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 文件hash
     */
    private String hash;
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 图片地址
     */
    private String url;
    /**
     * 删除地址
     */
    private String delete;
}
