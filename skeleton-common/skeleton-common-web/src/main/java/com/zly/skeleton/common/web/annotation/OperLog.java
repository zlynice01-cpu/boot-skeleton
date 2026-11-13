package com.zly.skeleton.common.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog
{
    /**
     * 模块标题
     */
    String title() default "";

    /**
     * 业务类型 0新增 1修改 2删除 3导出 4导入 5授权
     */
    int businessType() default 6;

    /**
     * 是否保存请求参数
     */
    boolean isSaveParam() default true;

    /**
     * 是否保存返回结果
     */
    boolean isSaveResult() default true;
}
