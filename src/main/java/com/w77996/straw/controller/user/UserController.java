package com.w77996.straw.controller.user;

import com.google.common.collect.Maps;
import com.w77996.straw.controller.BaseController;
import com.w77996.straw.core.result.Result;
import com.w77996.straw.core.result.ResultCode;
import com.w77996.straw.entity.UserDetailEntity;
import com.w77996.straw.entity.UserEntity;
import com.w77996.straw.entity.UserFileInfoEntity;
import com.w77996.straw.entity.dto.user.UserInfoDto;
import com.w77996.straw.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @description: 用户相关controller
 * @author: straw
 **/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;


    /**
     * 获取用户详细信息
     *
     * @return
     */
    @GetMapping("/info")
    public Result userInfo() {
        String userId = getUserIdByToken();
        UserEntity userInfo = userService.getUserById(userId);
        UserFileInfoEntity userFileInfo = userService.getUserFileInfoByUserId(userId);
        HashMap<String, Object> result = Maps.newHashMapWithExpectedSize(2);
        result.put("userInfo", userInfo);
        result.put("userFileInfo", userFileInfo);
        return Result.success(result);
    }

    /**
     * 获取用户详细信息
     *
     * @return
     */
    @GetMapping("/detail")
    public Result userDetailInfo() {
        String userId = getUserIdByToken();
        UserDetailEntity userDetailEntity = userService.getUserDetailById(userId);
        UserEntity userEntity = userService.getUserById(userId);
        HashMap<String, Object> result = Maps.newHashMapWithExpectedSize(2);
        result.put("userDetailInfo", userDetailEntity);
        result.put("userInfo", userEntity);
        return Result.success(result);
    }
    /**
     * 获取用户详细信息
     *
     * @return
     */
    @PostMapping("/info")
    public Result editUserInfo(@RequestBody UserInfoDto userInfoDto) {
        String userId = getUserIdByToken();
        UserEntity userInfo = userService.getUserById(userId);
        if (userInfo == null) {
            return Result.error(ResultCode.ERROR_RESOURCE_NO_FOUND);
        }
        userInfoDto.setUserId(Integer.parseInt(userId));
        userService.updateUserDetail(userInfoDto);
        return Result.success();
    }
}



