package com.w77996.straw.entity.dto.wx;

import lombok.Data;

/**
 * @description: 微信token
 * @author: straw
 **/
@Data
public class WxTokenDto {

    String openid;

    String session_key;
}
