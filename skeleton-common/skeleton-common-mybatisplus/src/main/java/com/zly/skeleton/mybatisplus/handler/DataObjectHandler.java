package com.zly.skeleton.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充
 *
 * @author ZLY
 */
public class DataObjectHandler implements MetaObjectHandler
{
    /**
     * 允许为 null：单元测试或非 Web 场景下可能不存在实现
     */
    private final AuditUserProvider auditUserProvider;

    public DataObjectHandler(AuditUserProvider auditUserProvider)
    {
        this.auditUserProvider = auditUserProvider;
    }

    @Override
    public void insertFill(MetaObject metaObject)
    {
        Long userId = currentUserId();
        this.setFieldValByNameIfNull("createBy", userId, metaObject);
        this.setFieldValByNameIfNull("updateBy", userId, metaObject);
        this.setFieldValByNameIfNull("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByNameIfNull("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByNameIfNull("isDeleted", "N", metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject)
    {
        this.setFieldValByNameIfNull("updateBy", currentUserId(), metaObject);
        this.setFieldValByNameIfNull("updateTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 获取当前操作人ID，无实现时返回 null
     */
    private Long currentUserId()
    {
        return auditUserProvider == null ? null : auditUserProvider.getCurrentUserId();
    }

    /**
     * 当没有值的时候再设置属性，如果有值则不设置。主要是方便单元测试
     * <p>
     * 值本身为 null 时不填充，避免把 NULL 显式写入 SQL 覆盖数据库默认值
     *
     * @param fieldName  字段名
     * @param fieldVal   待填充的值
     * @param metaObject 元对象
     */
    private void setFieldValByNameIfNull(String fieldName, Object fieldVal, MetaObject metaObject)
    {
        if (fieldVal != null && metaObject.getValue(fieldName) == null)
        {
            this.setFieldValByName(fieldName, fieldVal, metaObject);
        }
    }
}
