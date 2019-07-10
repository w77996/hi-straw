package com.w77996.straw.controller;

import com.w77996.straw.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: 基类
 * @author: straw
 **/
public class BaseController {

    @Autowired
    private ITokenService iTokenService;


    public String getUserIdByToken(){
        return iTokenService.getUserIdByToken();
    }
}
