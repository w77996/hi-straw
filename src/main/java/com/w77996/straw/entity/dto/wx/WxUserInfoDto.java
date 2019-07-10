package com.w77996.straw.entity.dto.wx;

import lombok.Data;

import java.util.Date;

/**
 * @description: 微信用户信息
 * @author: straw
 **/
@Data
public class WxUserInfoDto {

    Integer id;
    /**
     * 微信头像
     */
    String userLogo;

    /**
     * 微信用户名
     */
    String nickname;

    /**
     * 微信性别
     */
    Integer sex;

    /**
     * 用戶OpenId
     */
    String openId;

    /**
     * 最后登陆使劲按
     */
    Date lastLogin;

    /**
     * 所在地
     */
    String location;

    /**
     * 用户uuid
     */
    String uuid;
}
