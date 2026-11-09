package com.zly.skeleton.common.web.enums;

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

    ROLE_NOT_FOUND("未找到角色", 2001),

    USERNAME_EXIST("用户名已存在", 3001),
    USER_NOT_FOUND("用户未找到", 3002),

    CONFIG_NOT_FOUND("未找到配置", 4001),
    CONFIG_KEY_EXIST("参数键名已存在", 4002),
    CONFIG_BUILT_IN_NOT_DELETE("系统内置参数不允许删除", 4003),

    DICT_TYPE_NOT_FOUND("未找到字典类型", 4101),
    DICT_CODE_EXIST("字典编码已存在", 4102),
    DICT_DATA_NOT_FOUND("未找到字典数据", 4103),

    ;

    private final String msg;

    private final Integer code;

    BizErrorEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}
