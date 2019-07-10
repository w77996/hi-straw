package com.w77996.straw.entity;

import lombok.Data;

/**
 * @description: 用户信息
 * @author: straw
 **/
@Data
public class UserDetailEntity {

    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 平台
     */
    private String platform;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 位置
     */
    private String location;

}
