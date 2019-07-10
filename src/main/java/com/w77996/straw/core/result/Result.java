package com.w77996.straw.core.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @Description:
 * @Author straw
 */
@Data
public class Result<T> {

    private Integer code = 200;

    private String msg = "success";

    private T data;

    /**
     * 成功时候的调用
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static Result success() {
        return new Result();
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<T>(resultCode);
    }

    /**
     * 失败时候的调用
     */
    public static <T> Result<T> error(Integer code,String errorMsg) {
        return new Result<T>(code,errorMsg);
    }


    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result() {
    }

    private Result(ResultCode resultCode) {
        if (resultCode != null) {
            this.code = resultCode.getCode();
            this.msg = resultCode.getMsg();
        }
    }

}
