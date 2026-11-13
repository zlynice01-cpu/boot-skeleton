package com.zly.skeleton.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ZLY
 * @since 2026/11/11 14:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_oper_log")
public class SysOperLog extends BaseEntity
{
    private Long id;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 业务类型：0新增 1修改 2删除 3导出 4导入 5授权 6其他
     */
    private Integer businessType;

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求方式
     * GET POST PUT DELETE
     */
    private String requestMethod;

    /**
     * 操作人姓名
     */
    private String operName;

    /**
     * 操作人昵称
     */
    private String operNickname;

    /**
     * 部门id
     */
    private Long operDeptId;

    /**
     * 操作人ip
     */
    private String operIp;

    /**
     * 操作人地址
     */
    private String operLocation;

    /**
     * 请求地址
     */
    private String operUrl;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回结果
     */
    private String jsonResult;

    /**
     * 操作状态 0失败 1成功
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 耗时(ms)
     */
    private Long costTime;
}
