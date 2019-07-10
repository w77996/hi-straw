package com.w77996.straw.core.result;

/**
 * @Description:
 * @Author w77996
 * @Date 2019/3/8 22:20
 */
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200, "success"),
    /**
     * 系统错误
     */
    ERROR(-100, "error"),
    /**
     * 权限错误
     */
    ERROR_AUTH(-101, "auth error"),
    /**
     * 404 no found
     */
    ERROR_NO_FOUND(-102, "request not found"),

    /**
     * 权限错误
     */
    ERROR_TOKEN_NULL(-103, "token is null error"),

    /**
     * resource no found
     */
    ERROR_RESOURCE_NO_FOUND(-104, "resource not found"),
    /**
     * resource exist
     */
    ERROR_RESOURCE_EXIST(-105, "resource exist"),
    /**
     * 文件大小受限
     */
    SIZE_LIMIT(-110, "嗷~非vip只能上传小于5M文件~"),
    /**
     * 文件类型错误
     */
    FILE_TYPE_ERROR(-111, "嗷~非vip只能上传图片哦~"),
    /**
     * 文件大小受限
     */
    COUNT_LIMIT(-112, "嗷~非vip上传数量受限~"),
    /**
     * 接口忙碌限流
     */
    BUSY(-500, "busy");


    private Integer code;

    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
