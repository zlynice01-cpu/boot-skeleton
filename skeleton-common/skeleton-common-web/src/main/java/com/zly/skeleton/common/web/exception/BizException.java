package com.zly.skeleton.common.web.exception;

import com.zly.skeleton.common.web.enums.BaseError;

/**
 *
 * @author ZLY
 * @since 2026/10/26 9:29
 */
public class BizException extends RuntimeException
{

    private BaseError errorCode;

    public BizException(BaseError errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }

    public BizException(String message, BaseError errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BizException(String message, Throwable cause, BaseError errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BizException(Throwable cause, BaseError errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, BaseError errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public BaseError getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(BaseError errorCode) {
        this.errorCode = errorCode;
    }

}
