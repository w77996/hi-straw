package com.w77996.straw.service;

import com.w77996.straw.core.constant.PlatformConstant;
import com.w77996.straw.entity.UserDetailEntity;
import com.w77996.straw.entity.UserEntity;
import com.w77996.straw.entity.UserFileInfoEntity;
import com.w77996.straw.entity.dto.user.LoginDto;
import com.w77996.straw.entity.dto.user.UserInfoDto;
import com.w77996.straw.entity.dto.wx.WxUserInfoDto;
import com.w77996.straw.mapper.UserFileInfoMapper;
import com.w77996.straw.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author straw
 */
@Service
@Slf4j
public class IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserFileInfoMapper userFileInfoMapper;

    /**
     * 通过用户Id获取用户信息
     *
     * @param userId
     * @return
     */
    public UserDetailEntity getUserDetailById(String userId) {
        return userMapper.getUserDetailById(Integer.parseInt(userId));
    }

    /**
     * 通过手机号查找用户
     *
     * @param phone
     * @return
     */
    public UserEntity getUserByPhone(String phone) {
        UserEntity userEntity = userMapper.getUserByPhone(phone);
        return userEntity;
    }

    /**
     * 绑定用户与手机号
     *
     * @param userId
     * @param phone
     * @return
     */
    public Integer bindPhoneForUser(String userId, String phone) {
        return userMapper.bindPhoneForUser(Integer.parseInt(userId), phone);
    }

    /**
     * 登录
     *
     * @param loginDto
     * @return
     */
    public UserEntity loginByUserNameAndPassword(LoginDto loginDto) {

        return userMapper.getUserByUserNameAndPassword(loginDto);
    }

    /**
     * 通过openId获取用户
     *
     * @param openid
     * @return
     */
    public UserEntity getUserByOpenId(String openid) {
        return userMapper.getUserByOpenId(openid);
    }

    /**
     * 通过openId获取用户
     *
     * @param userId
     * @return
     */
    public UserEntity getUserById(String userId) {
        return userMapper.getUserById(Integer.parseInt(userId));
    }

    /**
     * 插入新用户
     *
     * @param newUser
     * @return
     */
    public Integer insertNewUser(UserEntity newUser) {
        return userMapper.insertNewUser(newUser);
    }


    /**
     * 插入新用户
     *
     * @param wxUserInfoDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer createNewUser(WxUserInfoDto wxUserInfoDto) {
        Integer res = userMapper.createNewUser(wxUserInfoDto);
        if (res > 0) {
            log.info(res + " " + wxUserInfoDto);
            //文件信息
            UserFileInfoEntity newUserFileInfoEntity = new UserFileInfoEntity();
            newUserFileInfoEntity.setUserId(wxUserInfoDto.getId());
            userFileInfoMapper.createNewUserFileInfo(newUserFileInfoEntity);
            //用户详细信息
            UserDetailEntity newUserDetailEntity = new UserDetailEntity();
            newUserDetailEntity.setUserId(wxUserInfoDto.getId());
            newUserDetailEntity.setSex(wxUserInfoDto.getSex());
            newUserDetailEntity.setLocation(wxUserInfoDto.getLocation());
            newUserDetailEntity.setPlatform(PlatformConstant.MiniApp.getValue());
            userMapper.createNewUserDetail(newUserDetailEntity);
        }
        return 1;
    }

    /**
     * 通过userId获取userFileInfo信息
     *
     * @param userId
     * @return
     */
    public UserFileInfoEntity getUserFileInfoByUserId(String userId) {
        return userFileInfoMapper.getUserFileInfoByUserId(Integer.parseInt(userId));
    }

    /**
     * 通过openId更新用户
     *
     * @param userEntity
     */
    public Integer updateUserByOpenId(UserEntity userEntity) {
        return userMapper.updateUserByOpenId(userEntity);
    }

    /**
     * 更新用户详细
     *
     * @param userInfoDto
     */
    public void updateUserDetail(UserInfoDto userInfoDto) {
        userMapper.updateUserDetail(userInfoDto);
    }
}
