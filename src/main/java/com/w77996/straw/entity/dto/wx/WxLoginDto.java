package com.w77996.straw.entity.dto.wx;

import lombok.Data;

/**
 * @description: 微信登陆dto
 * @author: straw
 **/
@Data
public class WxLoginDto {

    private String code;

    /**
     * 微信头像
     */
    private String userLogo;

    /**
     * 微信用户名
     */
    private String nickname;

    /**
     * 微信性别
     */
    private Integer sex;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;
}
