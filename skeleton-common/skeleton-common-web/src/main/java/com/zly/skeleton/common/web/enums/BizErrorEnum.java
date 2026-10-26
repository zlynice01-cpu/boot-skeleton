package com.zly.skeleton.common.web.enums;

import com.zly.skeleton.common.web.exception.BizException;
import lombok.Getter;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:31
 */
@Getter
public enum BizErrorEnum implements BaseError
{
    DEPT_NOT_FOUND("未找到部门", 1001),
    DEPT_EXPIRE("部门失效", 1002),

    ;

    private final String msg;

    private final Integer code;

    BizErrorEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
