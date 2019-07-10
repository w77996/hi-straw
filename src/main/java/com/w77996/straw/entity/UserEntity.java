package com.w77996.straw.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author straw
 */
@Data
public class UserEntity {

    private Integer id;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户logo
     */
    private String userLogo;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * unionId
     */
    private String unionId;
    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 手机号
     */
    private String phoneNum;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 上次登录时间
     */
    private Date lastLogin;


}
