package com.w77996.straw.service;

import com.w77996.straw.core.result.Result;
import org.springframework.stereotype.Service;

/**
 * @description: 用户与文件信息业务
 * @author: straw
 **/
@Service
public class IUserFileInfoService {



    public Result insertNewUserFileInfo(String userId){
        return Result.success();
    }

}
