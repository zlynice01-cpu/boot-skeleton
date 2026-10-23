package com.zly.skeleton.common.web.enums;

/**
 * 基础状态码类
 */
public enum BaseEnum implements BaseError
{

    // 后台处理成功
    SUCCESS("执行成功!", 200),

    // 后台处理失败
    FAIL("执行失败!", 100),

    // 后台处理失败
    RESOURCE_NOT_FOUND("资源没有找到!", 101),

    // 数组越界了！
    // INDEX_OUT_BOUNDS("数组越界了!", 102),
    INDEX_OUT_BOUNDS("执行失败!", 102),

    // 系统错误，未知的错误
    SYSTEM_ERROR("系统错误!", 103),

    // 空指针错误
    // NULL_POINTER("空指针异常!", 104),
    NULL_POINTER("执行失败!", 104),

    // IO异常
    // IO_EXCEPTION("IO异常!", 105),
    IO_EXCEPTION("执行失败!", 105),

    // 未知的方法
    NO_SUCH_METHOD("找不到方法，未知的方法!", 106),

    // 类型转换错误
    // CLASS_CAST("类型转换出错，请检查!", 107),
    CLASS_CAST("执行失败!", 107),

    // 文件未找到错误
    FILE_NOT_FOUND("文件没有找到，请确认文件位置!", 108),

    // 字符串转换数字异常
    // NUMBER_FORMAT("字符串转换数字出错了!", 109),
    NUMBER_FORMAT("执行失败!", 109),

    // 操作数据库错误
    // SQL_EXCEPTION("操作数据库出错了!", 110),
    SQL_EXCEPTION("执行失败!", 110),

    // 传递的参数出错了
    ILLEGAL_ARGUMENT("传递的参数出错了!", 111),

    // 堆栈溢出错误
    // STACK_OVERFLOW("栈溢出了!", 112),
    STACK_OVERFLOW("执行失败!", 112),

    // 用户未登录
    USER_NOTLOGGENIN("用户未登录!", 113),

    // 登录失败
    USER_LOGINERROR("登录失败!", 114),

    // 用户名或密码错误
    PASSWORD_ERROR("用户名或密码错误!", 115),

    // 验证码发送失败
    SEND_FAIL("验证码发送失败!", 116),

    // 缺少参数或值为空
    PARAMETER_IS_NULL("缺少参数或值为空!", 117),

    // 参数不合法
    PARAMETER_IS_WRONGFUL("参数不合法!", 118),

    // 无效的Token
    INVALID_TOKEN("无效的Token!", 119),

    // 无操作权限
    NO_OPERATION_AUTHORITY("无操作权限!", 120),

    //重复登录
    REPEAT_LOGIN("该账号已在别处登录!", 666),

    CUSTOMIZE_EXCEPTION("执行失败!", 121),

    // token过期了，请重新登录!
    LOGIN_AGIN("token过期了，请重新登录!", 122),

    // 第三方调用异常
    CONNECT_EXCEPTION("调用第三方接口失败!", 123),

    // 400错误
    BAD_REQUEST("Bad Request!", 400),

    // 405错误
    METHOD_NOT_ALLOWED("Method Not Allowed! 有可能是接口类型错误!", 405),

    // 404错误拦截
    NO_HANDLER("这个页面石沉大海了！接口没找到!", 404),

    BAD_ARGUMENT("参数不对", 401),

    BAD_ARGUMENT_VALUE("参数值不对", 402),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR("服务器内部错误!", 500),

    // 用户未登录
    UNLOGIN("请登录", 501),

    // 系统内部错误
    SERIOUS("系统内部错误", 502),

    // 业务不支持
    UNSUPPORT("业务不支持", 503),

    // 更新数据已经失效
    UPDATED_DATE_EXPIRED("更新数据已经失效", 504),

    // 更新数据失败
    UPDATED_DATA_FAILED("更新数据失败", 505),

    // 无操作权限
    UNAUTHZ("无操作权限", 506),

    ;


    private final Integer code;

    private final String msg;

    BaseEnum(String msg, Integer code) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode()
    {
        return code;
    }

    @Override
    public String getMsg()
    {
        return msg;
    }
}
