package com.w77996.straw.entity.dto.user;

import lombok.Data;


/**
 * @description: 用户信息
 * @author: straw
 **/
@Data
public class UserInfoDto {
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private String birthday;
}
