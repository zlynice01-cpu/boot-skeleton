package com.zly.skeleton.biz.enums;

import lombok.Getter;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:39
 */
@Getter
public enum StatusEnum
{
    YES(1, "有效"),

    NO(0, "失效")
    ;

    private final Integer code;

    private final String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
