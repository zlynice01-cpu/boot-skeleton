package com.zly.skeleton.common.web.entity;

import com.zly.skeleton.common.web.enums.BaseEnum;
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
        return new Result<>(BaseEnum.SUCCESS.getCode(), true, BaseEnum.SUCCESS.getMsg());
    }


    /**
     * 成功返回的json封装体，有数据
     *
     * @param data 返回的数据
     * @return json格式数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(BaseEnum.SUCCESS.getCode(), true, data, BaseEnum.SUCCESS.getMsg());
    }


    /**
     * 成功返回的json封装体，有数据，自定义提示信息
     *
     * @param data 返回的数据
     * @param msg  提示信息
     * @return json格式数据
     */
    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(BaseEnum.SUCCESS.getCode(), true, data, msg);
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
        return new Result<>(BaseEnum.FAIL.getCode(), false, BaseEnum.FAIL.getMsg());
    }


    /**
     * 失败返回的json封装体，自定义提示信息
     *
     * @return json格式数据
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(BaseEnum.FAIL.getCode(), false, msg);
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
        return error(BaseEnum.BAD_ARGUMENT);
    }

    public static <T> Result<T> badArgumentValue() {
        return error(BaseEnum.BAD_ARGUMENT_VALUE);
    }

    public static <T> Result<T> unlogin() {
        return error(BaseEnum.UNLOGIN);
    }

    public static <T> Result<T> serious() {
        return error(BaseEnum.SERIOUS);
    }

    public static <T> Result<T> unsupport() {
        return error(BaseEnum.UNSUPPORT);
    }

    public static <T> Result<T> updatedDateExpired() {
        return error(BaseEnum.UPDATED_DATE_EXPIRED);
    }

    public static <T> Result<T> updatedDataFailed() {
        return error(BaseEnum.UPDATED_DATA_FAILED);
    }

    public static <T> Result<T> unauthz() {
        return error(BaseEnum.UNAUTHZ);
    }

}
