package com.zly.skeleton.common.web.entity;

import com.zly.skeleton.common.web.enums.BaseErrorEnum;
import com.zly.skeleton.common.web.enums.BaseError;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 数据返回json格式封装类
 */
@Data
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1348172152215944560L;

    // 返回给前端的状态码
    private int code;

    // 成功返回true，失败返回flase
    private Boolean flag;

    // 返回给前端的数据
    private T data;

    // 后台提示信息
    private String msg;

    /**
     * 三参构造
     *
     * @param code 状态码
     * @param flag 是否成功
     * @param msg  提示信息
     */
    public Result(int code, Boolean flag, String msg) {
        this.code = code;
        this.flag = flag;
        this.msg = msg;
    }

    private Result(int code, Boolean flag, T data, String msg) {
        this.code = code;
        this.flag = flag;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 成功返回的json封装体，没有数据
     *
     * @return json格式数据
     */
    public static <T> Result<T> success() {
        return new Result<>(BaseErrorEnum.SUCCESS.getCode(), true, BaseErrorEnum.SUCCESS.getMsg());
    }


    /**
     * 成功返回的json封装体，有数据
     *
     * @param data 返回的数据
     * @return json格式数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(BaseErrorEnum.SUCCESS.getCode(), true, data, BaseErrorEnum.SUCCESS.getMsg());
    }


    /**
     * 成功返回的json封装体，有数据，自定义提示信息
     *
     * @param data 返回的数据
     * @param msg  提示信息
     * @return json格式数据
     */
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(BaseErrorEnum.SUCCESS.getCode(), true, data, msg);
    }

    /**
     * 成功返回的json封装体，有数据，自定义提示信息
     *
     * @param data 返回的数据
     * @param msg  提示信息
     * @return json格式数据
     */
    public static <T> Result<T> success(Integer code, T data, String msg) {
        return new Result<>(code, true, data, msg);
    }

    /**
     * 失败返回的json封装体
     *
     * @return json格式数据
     */
    public static <T> Result<T> error() {
        return new Result<>(BaseErrorEnum.FAIL.getCode(), false, BaseErrorEnum.FAIL.getMsg());
    }


    /**
     * 失败返回的json封装体，自定义提示信息
     *
     * @return json格式数据
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(BaseErrorEnum.FAIL.getCode(), false, msg);
    }


    /**
     * 失败返回的json封装体，自定义提示信息和状态码
     *
     * @param code 状态码
     * @param msg  提示信息
     * @return json格式数据
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, false, msg);
    }

    /**
     * 失败返回：直接传入错误码枚举（统一接口BaseErrorCode）
     */
    public static <T> Result<T> error(BaseError errorCode) {
        return new Result<>(errorCode.getCode(), false, errorCode.getMsg());
    }

    public static <T> Result<T> badArgument() {
        return error(BaseErrorEnum.BAD_ARGUMENT);
    }

    public static <T> Result<T> badArgumentValue() {
        return error(BaseErrorEnum.BAD_ARGUMENT_VALUE);
    }

    public static <T> Result<T> unlogin() {
        return error(BaseErrorEnum.UNLOGIN);
    }

    public static <T> Result<T> serious() {
        return error(BaseErrorEnum.SERIOUS);
    }

    public static <T> Result<T> unsupport() {
        return error(BaseErrorEnum.UNSUPPORT);
    }

    public static <T> Result<T> updatedDateExpired() {
        return error(BaseErrorEnum.UPDATED_DATE_EXPIRED);
    }

    public static <T> Result<T> updatedDataFailed() {
        return error(BaseErrorEnum.UPDATED_DATA_FAILED);
    }

    public static <T> Result<T> unauthz() {
        return error(BaseErrorEnum.UNAUTHZ);
    }

}
