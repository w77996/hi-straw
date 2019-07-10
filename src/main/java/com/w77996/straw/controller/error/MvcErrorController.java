package com.w77996.straw.controller.error;

import com.w77996.straw.core.result.Result;
import com.w77996.straw.core.result.ResultCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: mvc错误返回
 * @author: straw
 **/
@RestController
public class MvcErrorController implements ErrorController {
    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result handleError(HttpServletRequest request) {
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
            return Result.error(ResultCode.ERROR_AUTH);
        } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return Result.error(ResultCode.ERROR_NO_FOUND);
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            return Result.error(ResultCode.ERROR_NO_FOUND);
        } else {
            return Result.error(ResultCode.ERROR);
        }

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
