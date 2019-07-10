package com.w77996.straw.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.w77996.straw.core.annotation.IgnoreToken;
import com.w77996.straw.core.jwt.JwtHelper;
import com.w77996.straw.core.result.Result;
import com.w77996.straw.entity.UserEntity;
import com.w77996.straw.entity.dto.wx.WxLoginDto;
import com.w77996.straw.entity.dto.wx.WxTokenDto;
import com.w77996.straw.entity.dto.wx.WxUserInfoDto;
import com.w77996.straw.service.IUserService;
import com.w77996.straw.util.CommonUtils;
import com.w77996.straw.util.RestHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @description: 微信相关
 * @author: straw
 **/
@RestController
@RequestMapping("/wx")
@Slf4j
public class WxController {

    @Autowired
    private IUserService userService;

    @Value("${wx.appId}")
    private String wxAppId;

    @Value("${wx.appSec}")
    private String wxAppSec;

    /**
     * 通过code获取openId
     *
     * @param wxLoginDto
     * @return
     */
    @IgnoreToken
    @PostMapping("/code")
    public Result getUserInfoByCode(@RequestBody WxLoginDto wxLoginDto) {
        log.info("enter getUserInfoByCode");
        //微信授权获取openId
        String reqUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxAppId + "&secret=" + wxAppSec + "&js_code=" + wxLoginDto.getCode() + "&grant_type=authorization_code";
        JSONObject wxAuthObject = RestHttpClient.client(reqUrl, HttpMethod.GET, null);
        log.info("wxAuthObject {}", wxAuthObject.toJSONString());
        WxTokenDto wxTokenDto = JSONObject.parseObject(wxAuthObject.toJSONString(), WxTokenDto.class);
        log.info("wxTokenDto {}", wxTokenDto.toString());
        Map<String, Object> tokenMapper = Maps.newHashMapWithExpectedSize(2);
        //生成新用户
        UserEntity userEntity = userService.getUserByOpenId(wxTokenDto.getOpenid());
        if (!ObjectUtils.allNotNull(userEntity)) {
            WxUserInfoDto wxUserInfoDto = new WxUserInfoDto();
            wxUserInfoDto.setNickname(wxLoginDto.getNickname());
            wxUserInfoDto.setUserLogo(wxLoginDto.getUserLogo());
            wxUserInfoDto.setSex(wxLoginDto.getSex());
            wxUserInfoDto.setLastLogin(new Date());
            wxUserInfoDto.setOpenId(wxTokenDto.getOpenid());
            wxUserInfoDto.setUuid(CommonUtils.generateRandomNum(9));
            wxUserInfoDto.setLocation(StringUtils.join(new String[]{wxLoginDto.getCountry(), wxLoginDto.getProvince(), wxLoginDto.getCity()}, "-"));
            userService.createNewUser(wxUserInfoDto);
            log.info("create new user {}", wxUserInfoDto);
            userEntity = new UserEntity();
            userEntity.setId(wxUserInfoDto.getId());
        } else {
            //更新登陆时间
            userEntity.setLastLogin(new Date());
            userService.updateUserByOpenId(userEntity);
        }
        tokenMapper.put("token", JwtHelper.createJWT(userEntity.getId() + StringUtils.EMPTY));
        return Result.success(tokenMapper);
    }
}
