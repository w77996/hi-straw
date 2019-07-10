package com.w77996.straw.mapper;


import com.w77996.straw.entity.UserDetailEntity;
import com.w77996.straw.entity.UserEntity;
import com.w77996.straw.entity.dto.user.LoginDto;
import com.w77996.straw.entity.dto.user.UserInfoDto;
import com.w77996.straw.entity.dto.wx.WxUserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author straw
 */
@Mapper
public interface UserMapper {

    /**
     * 通过id获取用户
     *
     * @param userId
     * @return
     */
    UserEntity getUserById(@Param("id") Integer userId);

    /**
     * 通过手机查找用户
     *
     * @param phone
     * @return
     */
    UserEntity getUserByPhone(@Param("phone") String phone);

    /**
     * 绑定用户和手机
     *
     * @param userId
     * @param phone
     * @return
     */
    Integer bindPhoneForUser(@Param("id") Integer userId, @Param("phone") String phone);

    /**
     * 通过用户名和密码登录
     *
     * @param loginDto
     * @return
     */
    UserEntity getUserByUserNameAndPassword(LoginDto loginDto);

    /**
     * 通过openId获取用户
     *
     * @param openid
     * @return
     */
    UserEntity getUserByOpenId(@Param("openId") String openid);

    /**
     * 添加新用户
     *
     * @param newUser
     * @return
     */
    Integer insertNewUser(UserEntity newUser);

    /**
     * 创建新用户
     *
     * @param wxUserInfoDto
     * @return
     */
    Integer createNewUser(WxUserInfoDto wxUserInfoDto);

    /**
     * 创建用户详细
     *
     * @param newUserDetailEntity
     * @return
     */
    Integer createNewUserDetail(UserDetailEntity newUserDetailEntity);

    /**
     * 通过openId更新用户
     *
     * @param userEntity
     * @return
     */
    Integer updateUserByOpenId(UserEntity userEntity);

    /**
     * 更新用户详细
     *
     * @param userInfoDto
     * @return
     */
    Integer updateUserDetail(UserInfoDto userInfoDto);

    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    UserDetailEntity getUserDetailById(@Param("userId") Integer userId);
}
